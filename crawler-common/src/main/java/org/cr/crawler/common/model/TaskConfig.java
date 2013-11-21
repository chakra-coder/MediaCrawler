/**
 * 
 */
package org.cr.crawler.common.model;

import java.util.Date;

/**
 * 
 * @author caorong
 * 
 */
public class TaskConfig {
	private String id;
	private String configName;

	private Integer priority;

	private Date createTime;

	// for quartz
	private Date taskStartTime;
	private Date taskEndTime;
	private Integer repeatCount;
	private Long intervalSecond;

	public TaskConfig(String configName, Integer priority, Date createTime,
			Date taskStartTime, Date taskEndTime, Integer repeatCount,
			Long intervalSecond) {
		super();
		this.configName = configName;
		this.priority = priority;
		this.createTime = createTime;
		this.taskStartTime = taskStartTime;
		this.taskEndTime = taskEndTime;
		this.repeatCount = repeatCount;
		this.intervalSecond = intervalSecond;
	}

	@Override
	public String toString() {
		return "TaskConfig [id=" + id + ", configName=" + configName
				+ ", priority=" + priority + ", createTime=" + createTime
				+ ", taskStartTime=" + taskStartTime + ", taskEndTime="
				+ taskEndTime + ", repeatCount=" + repeatCount
				+ ", intervalSecond=" + intervalSecond + "]";
	}

	public String getConfigName() {
		return configName;
	}

	public void setConfigName(String configName) {
		this.configName = configName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getTaskStartTime() {
		return taskStartTime;
	}

	public void setTaskStartTime(Date taskStartTime) {
		this.taskStartTime = taskStartTime;
	}

	public Date getTaskEndTime() {
		return taskEndTime;
	}

	public void setTaskEndTime(Date taskEndTime) {
		this.taskEndTime = taskEndTime;
	}

	public Integer getRepeatCount() {
		return repeatCount;
	}

	public void setRepeatCount(Integer repeatCount) {
		this.repeatCount = repeatCount;
	}

	public Long getIntervalSecond() {
		return intervalSecond;
	}

	public void setIntervalSecond(Long intervalSecond) {
		this.intervalSecond = intervalSecond;
	}

}
