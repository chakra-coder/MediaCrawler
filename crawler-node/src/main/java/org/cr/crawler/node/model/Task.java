package org.cr.crawler.node.model;

import java.util.Date;

/**
 * Author: caorong Date: 13-11-9 Time: 下午5:08 To change this template use File |
 * Settings | File Templates.
 */
public class Task {
	private String id;
	// the real url to download
	private String targetUrl;

	private String nodename;

	private Integer status;
	private Integer priority;

	private String taskConfigId;

	private Integer retry;

	// list parse task or detail download task
	private Integer type;

	private Date createTime;
	private Date startTime;
	private Date finishTime;
	
	
	
}
