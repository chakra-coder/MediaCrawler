package org.cr.crawler.node.executor;

import org.cr.crawler.common.model.Task;
import org.cr.crawler.node.handle.CallbackHandler;
import org.cr.crawler.node.operation.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;


/**
 * Author: caorong
 * Date: 13-11-16
 * Time: 下午11:49
 * To change this template use File | Settings | File Templates.
 */
public class TaskRunner implements Runnable {

    private Logger logger = LoggerFactory.getLogger(TaskRunner.class);

    private Operation operation;

    private CallbackHandler handler;

    private Task task;

    private AtomicInteger count;

    public TaskRunner(Operation operation, CallbackHandler handler, Task task, AtomicInteger count) {
        this.operation = operation;
        this.handler = handler;
        this.task = task;
        this.count = count;
    }

    @Override
    public void run() {
        logger.info("start execute task[{}] with operation[{}] and handler[{}]", task, operation, handler);
        count.incrementAndGet();
        long start = System.currentTimeMillis();
        try {
            int currentTaskState = task.getState();
            Task retTask = operation.operate(task);
            if (currentTaskState == retTask.getState()) {
                retTask.setRetry(task.getRetry() + 1);
            }
            handler.handle(retTask);
        } finally {
            long stop = System.currentTimeMillis();
            long use = stop - start;
            count.decrementAndGet();
            logger.info("end execute task[{}] with operation[{}] and handler[{}],cost [{}] second", task, operation, handler, use / 1000.0);
            logger.info("{} tasks is in {} step!", count, operation);
        }

    }


}
