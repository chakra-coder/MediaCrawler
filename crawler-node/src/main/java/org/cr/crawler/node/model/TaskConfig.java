/**
 * 
 */
package org.cr.crawler.node.model;

import java.util.Date;

/**
 * 
 * @author caorong
 * 
 */
public class TaskConfig {
	private String id;

	private Integer prority;

	private Date createTime;

	// for quartz
	private Date taskStartTime;
	private Date taskEndTime;
	private Integer repeatCount;
	private Long intervalSecond;
	
	
	
}
