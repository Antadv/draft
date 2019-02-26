package com.somelogs.javase.thread.interrupt;

import java.util.concurrent.TimeUnit;

/**
 * 中断运行状态的线程
 *
 * @author LBG - 2019/2/26
 */
public class InterruptRunningThread {

    public static void main(String[] args) throws InterruptedException {
        interrupt1();
    }

    /**
     * 实例方法监测中断：public boolean isInterrupted()
     */
    private static void interrupt0() throws InterruptedException {
        Thread t = new Thread() {
            @Override
            public void run() {
                while (true) {
                    // 非阻塞状态下的下的线程需要手动进行中断监测
                    if (this.isInterrupted()) {
                        System.out.println("thread " + getName() + " is interrupted");
                        break;
                    }
                    System.out.println("thread " + this.getName() + " is running");
                }
            }
        };
        t.start();

        TimeUnit.SECONDS.sleep(1);
        t.interrupt();
    }

    private static void interrupt1() throws InterruptedException {
        Thread t = new Thread() {
            @Override
            public void run() {
                while (true) {
                    System.out.println("thread " + getName() + " state is " + this.isInterrupted());
                    if (Thread.interrupted()) {
                        System.out.println("thread " + getName() + " state is " + this.isInterrupted());
                        System.out.println("thread " + getName() + " is interrupted");
                        break;
                    }
                    System.out.println("thread " + this.getName() + " is running");
                }
            }
        };
        t.start();

        TimeUnit.SECONDS.sleep(1);
        t.interrupt();
    }
}
