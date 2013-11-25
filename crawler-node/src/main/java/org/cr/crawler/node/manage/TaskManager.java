/**
 *
 */
package org.cr.crawler.node.manage;

import org.cr.crawler.common.Constant;
import org.cr.crawler.common.model.Task;
import org.cr.crawler.common.service.TaskService;
import org.cr.crawler.node.executor.TaskExecutor;
import org.cr.crawler.node.manage.strategy.IStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 循环申请执行任务
 *
 * @author caorong
 */
@Component
public class TaskManager {
    private Logger logger = LoggerFactory.getLogger(TaskManager.class);

    private volatile boolean run = false;

    @Value("${cralwer.node.taskmanager.maxretry}")
    private int maxRetry;

    @Value("${crawler.node.name}")
    private String name;

    @Value("${crawler.node.requestInterval}")
    private Integer requesttInterval;

    private Lock lock = new ReentrantLock();

    private Condition condition = lock.newCondition();

    private Map<Integer, AtomicInteger> runningTaskCounts;

    private HashMap<Integer, TaskExecutor> listExecutors;

    private HashMap<Integer, TaskExecutor> detailExecutors;

    private Integer listExecutorType;

    private Integer detailExecutorType;

    private LinkedBlockingQueue<Task> taskQueue = new LinkedBlockingQueue<Task>();

    @Autowired
    private IStrategy strategy;

    // 记录当前CallBack 的数量
    private volatile AtomicInteger poolCount = new AtomicInteger();
    private Set<Task> runningTask = Collections
            .synchronizedSet(new HashSet<Task>());

    @Autowired
    private TaskService taskService;

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
            this.loadRemainTask();
            this.runloop();
        } catch (Exception e) {
            logger.error("运行时异常", e);
        }
    }

    private void runloop() {
        List<Integer> state_codes = new ArrayList<Integer>(2);
        state_codes.add(Constant.TASK_STATE_INITIAL);
        state_codes.add(Constant.TASK_STATE_RETRYING);
        List<Integer> requestTaskType = null;
        Integer requestInterval = 30 * 1000;
        Task task = null;
        while (run) {
            requestTaskType = strategy.analysis(runningTaskCounts);
            if (requestTaskType.size() == 0) {
                lock.lock();
                try {
                    condition.await(requestInterval, TimeUnit.SECONDS);
                    continue;
                } catch (InterruptedException e) {
                    logger.error("运行时异常", e);
                } finally {
                    lock.unlock();
                }
            }
            try {
                task = null;
                task = taskQueue.poll();// 先拿老的任务
                if (task != null)
                    System.out.println("working in run 2 !!! " + lock.toString() + "   task : " + task.toString());
                if (task == null)
                    task = taskService.selectFirstTask(name, state_codes, requestTaskType);
            } catch (RuntimeException e) {
                logger.error("向中心端请求任务异常", e);
                requestInterval *= 2;
            }
            if (task == null) {
                lock.lock();
                try {
                    condition.await(requestInterval, TimeUnit.SECONDS);
                    continue;
                } catch (InterruptedException e) {
                    logger.error("运行时异常", e);
                } finally {
                    lock.unlock();
                }
            } else {
                requestInterval = 30 * 1000;
                // upsert操作
                task.setRetry(0);
                dispatchTask(task);
            }
        }
    }

    // 先从db里面取出未完成的任务
    private void loadRemainTask() {
        logger.info("任务管理器开始加载遗留任务...");
//        taskService.selectOldTask();
        taskQueue.addAll(taskService.selectOldTask());
    }

    public void returnTask(Task task) {
        // poolCount -1
        poolCount.decrementAndGet();
        // 更新任务在taskState的状态或者retry
        if (task.getRetry() >= maxRetry) {
            task.setState(Constant.TASK_STATE_FAILED);
        }

        taskService.updateState(task);
        dispatchTask(task);
        signal();
    }

    private void signal() {
        lock.lock();
        try {
            condition.signal();
        } finally {
            lock.unlock();
        }
    }

    private void dispatchTask(Task task) {
        HashMap<Integer, TaskExecutor> executors = null;
        if (listExecutorType.equals(task.getType())) {
            executors = listExecutors;
        } else if (detailExecutorType.equals(task.getType())) {
            executors = detailExecutors;
        }
        TaskExecutor taskExecutor = executors.get(task.getState());
        if (task.getRetry() > maxRetry) {
            logger.warn("任务{0}已经达到最大重试次数...", task);
        } else if (taskExecutor != null) {
            taskExecutor.execute(task);
            runningTask.add(task);
            logger.info("任务{0}成功分配...", task);
            return;
        } else if (Constant.TASK_STATE_SUCCESS.equals(task.getState())) {
            logger.info("任务{0}执行完毕...", task);
        } else {
            logger.warn("任务{0}没有找到合适的执行者....被丢弃...", task);
        }
        runningTask.remove(task);
    }

    /**
     * create ThreadPoolExecutors & binding detailtask and listtask 's executors
     */
    // @PostConstruct
    public void init() {
        // create ThreadPoolExecutors
        System.out.println();
        // binding executor's count
        runningTaskCounts = new HashMap<Integer, AtomicInteger>();
        for (Entry<Integer, TaskExecutor> entry : detailExecutors.entrySet()) {
            runningTaskCounts.put(entry.getKey(), entry.getValue().getCount());
        }
        for (Entry<Integer, TaskExecutor> entry : listExecutors.entrySet()) {
            runningTaskCounts.put((entry.getKey() + 10), entry
                    .getValue().getCount());
        }
        System.out.println(runningTaskCounts);
    }

    public AtomicInteger getPoolCount() {
        return poolCount;
    }
}
