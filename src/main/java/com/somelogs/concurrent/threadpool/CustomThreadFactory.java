package com.somelogs.concurrent.threadpool;

import java.util.concurrent.*;

/**
 * 自定义线程工厂
 * @author LBG - 2017/9/8 0008
 */
public class CustomThreadFactory implements ThreadFactory {

    private final String poolName;

    public CustomThreadFactory(String poolName) {
        this.poolName = poolName;
    }

    @Override
    public Thread newThread(Runnable r) {
        return new CustomThread(r);
    }

    public static void main(String[] args) {
        int threadNum = Runtime.getRuntime().availableProcessors();
        final int queueCapacity = 2;

        ThreadPoolExecutor executor = new ThreadPoolExecutor(threadNum,
                                                             threadNum,
                                                0L,
                                                             TimeUnit.MILLISECONDS,
                                                             new LinkedBlockingQueue<Runnable>(queueCapacity),
                                                             new CustomThreadFactory("Broker-Thread-Factory"));
        // set saturation policy
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        //executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
        //executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardOldestPolicy());
        //executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());


        for (int i = 0; i < 10; i++) {
            try {
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println(Thread.currentThread().getName());
                    }
                });
            } catch(RejectedExecutionException e) {
                System.out.println("task queue is full, please wait!");
            }
        }
    }
}
