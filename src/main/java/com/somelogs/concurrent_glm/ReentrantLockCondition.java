package com.somelogs.concurrent_glm;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 重入锁的好搭档：Condition
 * @author LBG - 2017/9/22 0022
 */
public class ReentrantLockCondition implements Runnable {

    public static ReentrantLock lock = new ReentrantLock();
    public static Condition condition = lock.newCondition();

    @Override
    public void run() {
        lock.lock();
        try {
            // await 使当前线程等待，并释放锁
            condition.await();
            System.out.println("Thread is going on");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(new ReentrantLockCondition());
        t.start();
        Thread.sleep(2000);
        lock.lock();

        // 唤醒等待的线程
        condition.signal();

        // 这里注意释放锁，如果没释放，即使唤醒等待线程
        // 但是由于它无法重新获取锁，也不能真正继续执行
        lock.unlock();
    }
}
