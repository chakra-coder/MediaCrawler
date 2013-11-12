/**
 * 
 */
package org.cr.crawler.common.utils;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;

/**
 * a Blocking threadPoolExecutor
 * 
 * @author caorong
 * 
 */
public class BlockingThreadPoolExecutor extends ThreadPoolExecutor {

	public BlockingThreadPoolExecutor(int corePoolSize,
			BlockingQueue<Runnable> workQueue) {

		super(corePoolSize, corePoolSize, 0, TimeUnit.SECONDS, workQueue,
				defaultHandler);
	}

	public BlockingThreadPoolExecutor(int corePoolSize, String poolName,
			BlockingQueue<Runnable> workQueue) {
		super(corePoolSize, corePoolSize, 0, TimeUnit.SECONDS, workQueue,
				new BasicThreadFactory.Builder()
						.namingPattern(poolName + "-%d")
						.priority(Thread.MAX_PRIORITY).build(), defaultHandler);
	}

	private static final RejectedExecutionHandler defaultHandler = new BlockingRejectHandler();

	private static class BlockingRejectHandler implements
			RejectedExecutionHandler {

		@Override
		public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
			try {
				executor.getQueue().put(r);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
