package org.cr.crawler.node.executor;

import org.cr.crawler.common.model.Task;
import org.cr.crawler.common.utils.BlockingThreadPoolExecutor;
import org.cr.crawler.node.handle.CallbackHandler;
import org.cr.crawler.node.operation.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Author: caorong
 * Date: 13-11-16
 * Time: 下午9:37
 * To change this template use File | Settings | File Templates.
 */
public class SimpleTaskExecutor implements TaskExecutor {
    private Logger logger = LoggerFactory.getLogger(SimpleTaskExecutor.class);

    protected Operation operation;

    protected ExecutorService executorService;

    private int poolSize;

    private int queueSize;

    private String poolName;

    private volatile AtomicInteger count = new AtomicInteger();

    protected CallbackHandler handler;

    public void init() {
        if (poolName == null) {
            executorService = new BlockingThreadPoolExecutor(poolSize, new ArrayBlockingQueue<Runnable>(queueSize));
        } else {
            executorService = new BlockingThreadPoolExecutor(poolSize, poolName, new ArrayBlockingQueue<Runnable>(
                    queueSize));
        }
        logger.debug("initialize taskexecutor with operation={},poolSize={},queueSize={},poolName={},handler={}",
                operation, poolSize, queueSize, poolName, handler);
    }

    @Override
    public void execute(Task task) {
        TaskRunner taskRunner = new TaskRunner(operation, handler, task, count);
        logger.debug("prepare to execute task[{}] with operation[{}] and handler[{}]", task, operation, handler);
        executorService.submit(taskRunner);
    }

    @Override
    public void refresh() {
    }

    @Override
    public AtomicInteger getCount() {
        return count;
    }


    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public int getPoolSize() {
        return poolSize;
    }

    public void setPoolSize(int poolSize) {
        this.poolSize = poolSize;
    }

    public void setPoolName(String poolName) {
        this.poolName = poolName;
    }

    public void setHandler(CallbackHandler handler) {
        this.handler = handler;
    }

    public int getQueueSize() {
        return queueSize;
    }

    public void setQueueSize(int queueSize) {
        this.queueSize = queueSize;
    }
}
