package com.somelogs.concurrent.concurrent_ftf.aqs;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 共享式获取
 *
 * @author LBG - 2017/11/28 0028
 */
public class TwinsLock implements Lock {

    private Sync sync = new Sync(2);

    private static final class Sync extends AbstractQueuedSynchronizer {
        private Sync(int count) {
            if (count <= 0) {
                throw new IllegalArgumentException("count must larger than 0");
            }
            setState(count);
        }

        @Override
        protected int tryAcquireShared(int reduceCount) {
            for(;;) {
                int currentCount = getState();
                int newCount = currentCount - reduceCount;
                if (newCount < 0 || compareAndSetState(currentCount, newCount)) {
                    return newCount;
                }
            }
        }

        @Override
        protected boolean tryReleaseShared(int returnCount) {
            for(;;) {
                int currentCount = getState();
                int newCount = currentCount + returnCount;
                if (compareAndSetState(currentCount, newCount)) {
                    return true;
                }
            }
        }
    }

    @Override
    public void lock() {
        sync.acquireShared(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireSharedInterruptibly(1);
    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {
        sync.releaseShared(1);
    }

    @Override
    public Condition newCondition() {
        return null;
    }

    /************************ test ************************/

    public static void main(String[] args) {
        final Lock lock = new TwinsLock();

        class Worker extends Thread {
            @Override
            public void run() {
                while (true) {
                    lock.lock();
                    try {
                        Thread.sleep(1000L);
                        System.out.println(Thread.currentThread().getName());
                        Thread.sleep(1000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        lock.unlock();
                    }
                }
            }
        }

        for (int i = 0; i < 10; i++) {
            Worker worker = new Worker();
            worker.setDaemon(true);
            worker.setName("thread-" + i);
            worker.start();
        }

        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(1000L);
                System.out.println();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
