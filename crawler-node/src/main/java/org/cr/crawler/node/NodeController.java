package org.cr.crawler.node;

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
import java.util.Date;

/**
 * Node controller
 * start worker
 * start jetty to see logs remote  xx:9000/logs
 * Author: caorong
 * Date: 13-11-10
 * Time: 下午4:42
 */

@Component
public class NodeController {

    Logger logger = LoggerFactory.getLogger(NodeController.class);

    @Value("${crawler.node.name}")
    private String name;

    @Autowired
    private NodeService nodeService;
    Server server;

    @PostConstruct
    public void startNode() {
//        logger.info("123123 [{}]","123");
        // start jetty
//        configureServer();

        while (true) {
            // update the node information
            runReport();

            try {
                Thread.sleep(1000L * 10);
            } catch (InterruptedException e) {
            }
        }
//        System.out.println(1111);
    }

    private void runReport() {
        nodeService.insertOrUpdateNode(new Node(name, new Date()));
    }

    @PreDestroy
    public void finishNode() {
        System.out.println(123123 + "1231-->>>");
    }

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
