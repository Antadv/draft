package com.somelogs.threadpool;

import java.util.concurrent.Executors;

/**
 * 默认线程池实现
 *
 * corePoolSize     - 线程池的基本大小
 * maximumPoolSize  - 最大大小
 * keepAliveTime    - 线程存活时间，如果某个线程空闲时间超过了存活时间，那么将被标记为可回收
*                              并且当线程池的当前大小超过基本大小时，这个线程将被终止
 * TimeUnit unit    - 存活时间时间单位
 *
 * BlockingQueue<Runnable> workQueue    - 存放任务的阻塞队列
 * ThreadFactory threadFactory          - 线程工厂，用于创建线程
 * RejectedExecutionHandler handler     - 饱和策略
 *      AbortPolicy         - 抛出 RejectedExecutionException
 *      DiscardPolicy       - 直接抛弃任务
 *      DiscardOldestPolicy - 抛弃下一个将被执行的任务，然后尝试重新提交新的任务
 *                            如果工作队列是一个优先队列，那么将会抛弃优先级最高的任务
 *      CallerRunsPolicy    - 不会抛弃任务，也不会抛出异常，他不会在线程池的某个线程中执行新提交的任务
 *                            而是在一个调用了 execute 的线程中执行该任务
 *
 * @author LBG - 2017/9/8 0008
 */
public class DefaultThreadPoolExecutor {

    private final int processorCount = Runtime.getRuntime().availableProcessors();

    /**
     * newFixedThreadPool
     *
     * corePoolSize = maximumPoolSize = nThreads
     * 无界的 LinkedBlockingQueue
     */
    public void newFixedThreadPoolExecutor(int nThreads) {
        Executors.newFixedThreadPool(nThreads);
    }

    /**
     * newSingleThreadExecutor
     *
     * corePoolSize = maximumPoolSize = 1
     * 无界的 LinkedBlockingQueue
     */
    public void newSingleThreadExecutor() {
        Executors.newSingleThreadExecutor();
    }

    /**
     * newCachedThreadPool
     *
     * corePoolSize = 0
     * maximumPoolSize = Integer.MAX_VALUE
     * keepAliveTime = 60s
     * SynchronousQueue<Runnable>
     *     SynchronousQueue 不是一个真正的队列，而是一种在线程之间进行移交的机制
     *     使用直接移交将更高效，因为任务直接移交给执行线程，而不是被首先放到队列中
     *     所以只有当线程池是无界的或者可以拒绝任务时，SynchronousQueue 才有实际价值
     */
    public void newCachedThreadPool() {
        Executors.newCachedThreadPool();
    }
}
