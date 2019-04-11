package com.somelogs.concurrent.concurrent_glm;

/**
 * 线程中断
 * @see Thread#interrupt()      中断线程，设置中断标志位
 * @see Thread#isInterrupted()  判断是否被中断
 * @see Thread#interrupted()    判断是否被中断，并且清除当前中断状态
 *
 * @author LBG - 2017/9/21 0021
 */
public class ThreadInterrupt {

    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread() {
            @Override
            public void run() {
                while (true) {
                    if (Thread.currentThread().isInterrupted()) {
                        System.out.println("interrupted!");
                        break;
                    }
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        System.out.println("interrupted when sleeping!");
                        Thread.currentThread().interrupt();
                    }
                    Thread.yield();
                }
            }
        };
        t.start();
        Thread.sleep(2000);
        t.interrupt();
    }
}
