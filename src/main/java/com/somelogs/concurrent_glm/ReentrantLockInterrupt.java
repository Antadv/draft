package com.somelogs.concurrent_glm;

import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock 中断响应
 * @author LBG - 2017/9/22 0022
 */
public class ReentrantLockInterrupt implements Runnable {

    private static ReentrantLock lock1 = new ReentrantLock(false);
    private static ReentrantLock lock2 = new ReentrantLock(false);
    private int lock;

    public ReentrantLockInterrupt(int lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        try {
            if (lock == 1) {
                lock1.lockInterruptibly();
                Thread.sleep(500);
                lock2.lockInterruptibly();
            } else {
                lock2.lockInterruptibly();
                Thread.sleep(500);
                lock1.lockInterruptibly();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (lock1.isHeldByCurrentThread()) {
                System.out.println(Thread.currentThread().getName() + " 释放锁lock1");
                lock1.unlock();
            }
            if (lock2.isHeldByCurrentThread()) {
                System.out.println(Thread.currentThread().getName() + " 释放锁lock2");
                lock2.unlock();
            }
            System.out.println(Thread.currentThread().getName() + " 线程退出");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ReentrantLockInterrupt r1 = new ReentrantLockInterrupt(1);
        ReentrantLockInterrupt r2 = new ReentrantLockInterrupt(2);
        Thread t1 = new Thread(r1);
        Thread t2 = new Thread(r2);
        t1.start();
        t2.start();
        Thread.sleep(1000);
        t2.interrupt();
    }
}
