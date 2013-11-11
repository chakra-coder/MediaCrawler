/**
 * 
 */
package org.cr.crawler.node.manage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;

import org.cr.crawler.node.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

/**
 * 循环申请执行任务
 * 
 * @author caorong
 * 
 */
@Component
public class TaskManager {
	private Logger logger = LoggerFactory.getLogger(TaskManager.class);

	private volatile boolean run = false;

	@Value("${crawler.node.name}")
	private String name;

	@Value("${crawler.node.requestInterval}")
	private Integer requesttInterval;

	private Map<Short, AtomicInteger> runningTaskCounts;

	public boolean isStop() {
		return !run;
	}

	public boolean isRunning() {
		return run;
	}

	public void stop() {
		run = false;
	}

	public synchronized void start() {
		if (run) {
			return;
		}
		run = true;
		try {
			this.runloop();
		} catch (Exception e) {
			logger.error("运行时异常", e);
		}
	}

	private void runloop() {
		List<Short> state_codes = new ArrayList<Short>(2);
		state_codes.add(Constant.TASK_STATE_INITIAL);
		state_codes.add(Constant.TASK_STATE_RETRYING);
		while (run) {

		}
	}

	/**
	 * binding detailtask and listtask 's executors
	 */
	public void init() {
		runningTaskCounts = new HashMap<Short, AtomicInteger>();
		for (Entry<Short, TaskExecutor> entry : detailExecutors.entrySet()) {
			runningTaskCounts.put(entry.getKey(), entry.getValue().getCount());
		}
		for (Entry<Short, TaskExecutor> entry : listExecutors.entrySet()) {
			runningTaskCounts.put((short) (entry.getKey() + 10), entry
					.getValue().getCount());
		}
	}
}
