/**
 * 
 */
package org.cr.crawler.node.executor;

import java.util.concurrent.atomic.AtomicInteger;

import org.cr.crawler.node.model.Task;

/**
 * @author caorong
 * 
 */
public interface TaskExecutor {
	void execute(Task task);

	void refresh();

	AtomicInteger getCount();
}
