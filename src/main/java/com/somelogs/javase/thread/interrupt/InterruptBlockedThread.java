package com.somelogs.javase.thread.interrupt;

import java.util.concurrent.TimeUnit;

/**
 * 中断处于阻塞状态线程
 *
 * @author LBG - 2019/2/26
 */
public class InterruptBlockedThread {

    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException e) {
                        // 这里要注意：抛出一个InterruptedException的异常，同时中断状态将会被复位
                        // 所以当前线程的中断状态为false
                        System.out.println("interrupt state " + this.isInterrupted());
                        System.out.println("thread " + this.getName() + " is interrupted");
                        break;
                    }
                }
            }
        };
        t.start();

        TimeUnit.SECONDS.sleep(2);
        t.interrupt();
    }
}
