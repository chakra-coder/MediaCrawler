package org.cr.crawler.node.handle;

import org.cr.crawler.common.model.Task;
import org.cr.crawler.common.utils.BlockingThreadPoolExecutor;
import org.cr.crawler.node.manage.TaskManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Author: caorong
 * Date: 13-11-16
 * Time: 下午11:55
 * To change this template use File | Settings | File Templates.
 */
public class SimpleCallbackHandler implements CallbackHandler {

    private Logger logger = LoggerFactory.getLogger(SimpleCallbackHandler.class);

    private ExecutorService executorService = new BlockingThreadPoolExecutor(20, "CallBackPool",
            new LinkedBlockingQueue<Runnable>());

    @Autowired
    private TaskManager taskManager;


    @Override
    public void handle(Task task) {
        logger.debug("handle task[{}]", task);

        Runnable runner = new CallbackRunner(task, taskManager);
        // poolCount +1
        taskManager.getPoolCount().addAndGet(1);
        executorService.submit(runner);
    }


    private static class CallbackRunner implements Runnable {

        private Task task;

        private TaskManager tm;

        private CallbackRunner(Task task, TaskManager tm) {
            this.task = task;
            this.tm = tm;
        }

        @Override
        public void run() {
            tm.returnTask(task);
        }

    }

    @Override
    public String toString() {
        return super.toString();
    }
}
