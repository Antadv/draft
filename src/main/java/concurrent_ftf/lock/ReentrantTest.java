package concurrent_ftf.lock;

import concurrent_ftf.aqs.Mutex;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 可重入性测试
 * synchronized
 * ReentrantLock
 * Mutex 通过 AQS 自定义的锁，不具备可重入性
 *
 * @author LBG - 2017/11/1 0001
 */
public class ReentrantTest {

    private static ReentrantLock lock = new ReentrantLock();
    private static Mutex mutex = new Mutex();

    public static void main(String[] args) {
        // synchronized
        syncA();

        // ReentrantLock
        lockA();

        // Mutex
        mutexA();
    }

    private synchronized static void syncA() {
        System.out.println("do sth in syncA!");
        syncB();
    }

    private synchronized static void syncB() {
        System.out.println("do sth in syncB!");
    }

    private static void lockA() {
        lock.lock();
        try {
            System.out.println("do sth in lockA!");
            lockB();
        } finally {
            lock.unlock();
        }
    }

    private static void lockB() {
        lock.lock();
        try {
            System.out.println("do sth in lockB!");
        } finally {
            lock.unlock();
        }
    }

    private static void mutexA() {
        mutex.lock();
        try {
            System.out.println("do sth in mutexA!");
            // 由于 Mutex 不具备可重入性，线程会一直阻塞在 mutexB()
            mutexB();
        } finally {
            mutex.unlock();
        }
    }

    private static void mutexB() {
        mutex.lock();
        try {
            System.out.println("do sth in mutexA!");
        } finally {
            mutex.unlock();
        }
    }



}
