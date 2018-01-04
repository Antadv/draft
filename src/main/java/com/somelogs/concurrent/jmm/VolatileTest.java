package com.somelogs.concurrent.jmm;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * volatile demo
 *
 * @author LBG - 2017/10/27 0027
 */
public class VolatileTest {

    private static volatile int race = 0;

    private static void increase() {
        race++;
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService threadPool = Executors.newFixedThreadPool(20);
        final CountDownLatch latch = new CountDownLatch(20);
        for (int i = 0; i < 20; i++) {
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 10000; j++) {
                        increase();
                    }
                    latch.countDown();
                }
            });
        }
        latch.await();
        System.out.println(race);
        threadPool.shutdown();
    }
}
