package org.cr.crawler.common.model;

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

	private Integer state;
	private Integer priority;

	private String taskConfigId;

	private Integer retry;

	// list parse task or detail download task
	private Integer type;

	private Date createTime;
	private Date startTime;
	private Date finishTime;

	public Task(String id, String targetUrl, String nodename, Integer state,
			Integer priority, String taskConfigId, Integer type, Date createTime) {
		super();
		this.id = id;
		this.targetUrl = targetUrl;
		this.nodename = nodename;
		this.state = state;
		this.priority = priority;
		this.taskConfigId = taskConfigId;
		this.type = type;
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "Task [id=" + id + ", targetUrl=" + targetUrl + ", nodename="
				+ nodename + ", state=" + state + ", priority=" + priority
				+ ", taskConfigId=" + taskConfigId + ", retry=" + retry
				+ ", type=" + type + ", createTime=" + createTime
				+ ", startTime=" + startTime + ", finishTime=" + finishTime
				+ "]";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTargetUrl() {
		return targetUrl;
	}

	public void setTargetUrl(String targetUrl) {
		this.targetUrl = targetUrl;
	}

	public String getNodename() {
		return nodename;
	}

	public void setNodename(String nodename) {
		this.nodename = nodename;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public String getTaskConfigId() {
		return taskConfigId;
	}

	public void setTaskConfigId(String taskConfigId) {
		this.taskConfigId = taskConfigId;
	}

	public Integer getRetry() {
		return retry;
	}

	public void setRetry(Integer retry) {
		this.retry = retry;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

}
