package com.somelogs.concurrent.threadpool;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 扩展 ThreadPoolExecutor
 * @author LBG - 2017/9/8 0008
 */
public class ExtensionThreadPool extends ThreadPoolExecutor {

    private final ThreadLocal<Long> startTime = new ThreadLocal<>();
    private final AtomicLong totalTime = new AtomicLong();
    private final AtomicLong numTasks = new AtomicLong();

    public ExtensionThreadPool(int corePoolSize,
                               int maximumPoolSize,
                               long keepAliveTime,
                               TimeUnit unit,
                               BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
        startTime.set(System.nanoTime());
        System.out.println("before execute...");
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);
        numTasks.incrementAndGet();
        long endTime = System.nanoTime();
        long taskTime = endTime - startTime.get();
        totalTime.addAndGet(taskTime);
        System.out.println();
    }
}
