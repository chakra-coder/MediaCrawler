package org.cr.crawler.node;

import org.cr.crawler.node.manage.TaskManager;
import org.cr.crawler.node.model.Node;
import org.cr.crawler.node.service.NodeService;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

/**
 * Node controller <br>
 * start worker. <br>
 * start jetty to see logs remote xx:9000/logs <br>
 * Author: caorong Date: 13-11-10 Time: 下午4:42
 */

@Component
public class NodeController {

	private Logger logger = LoggerFactory.getLogger(NodeController.class);

	private String localIpAddress;
	@Value("${crawler.node.name}")
	private String name;

	@Value("${crawler.node.reportInterval}")
	private Integer reportInterval;

	@Autowired
	private NodeService nodeService;
	Server server;

	@Autowired
	private TaskManager taskManager;

	@PostConstruct
	public void startNode() {
		// set current ip
		try {
			localIpAddress = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e1) {
			logger.error(
					"get ip address error ,localIpAddress set to 127.0.0.1", e1);
			localIpAddress = "127.0.0.1";
		}
		// start jetty
		// configureServer();

		while (true) {
			try {
				// update the node information (worker's heartbeat)
				runReport();
				if (taskManager.isStop()) {
					new Thread(new Runnable() {

						@Override
						public void run() {
							taskManager.start();
						}
					}, name).start();
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				if (taskManager.isRunning()) {
					taskManager.stop();
				}
			}
			try {
				Thread.sleep(1000L * reportInterval);
			} catch (InterruptedException e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	private void runReport() {
		nodeService.insertOrUpdateNode(new Node(name, new Date(),
				localIpAddress));
	}

	// @PreDestroy
	// public void finishNode() {
	// System.out.println(123123 + "1231-->>>");
	// }

	/**
	 * config logs's path with jetty
	 */
	void configureServer() {
		// set log web directory
		server = new Server(9000);
		WebAppContext webapp = new WebAppContext();
		webapp.setContextPath("/logs");
		webapp.setResourceBase("./logs");
		server.setHandler(webapp);
		try {
			server.start();
		} catch (Exception e) {
			logger.error("jetty start error", e);
		}
	}
}
