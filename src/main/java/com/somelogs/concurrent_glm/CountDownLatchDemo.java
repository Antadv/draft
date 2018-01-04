package com.somelogs.concurrent_glm;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 倒计时器：CountDownLatch
 * @author LBG - 2017/9/22 0022
 */
public class CountDownLatchDemo implements Runnable {

    private static CountDownLatch latch;

    public CountDownLatchDemo(int count) {
        latch = new CountDownLatch(count);
    }

    @Override
    public void run() {
        try {
            Thread.sleep(500);
            System.out.println("check complete!");
            latch.countDown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        CountDownLatchDemo latchDemo = new CountDownLatchDemo(10);
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            executorService.execute(latchDemo);
        }

        latch.await();
        System.out.println("Fire in the hole!");
        executorService.shutdown();
    }
}
