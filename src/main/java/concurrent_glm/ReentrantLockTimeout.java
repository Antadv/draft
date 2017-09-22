package concurrent_glm;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Reentrant 申请等待限时
 * @see ReentrantLock#tryLock()                 尝试获取锁，获取失败立即返回false
 * @see ReentrantLock#tryLock(long, TimeUnit)   尝试获取锁，指定时间后获取失败返回false
 *
 * @author LBG - 2017/9/22 0022
 */
public class ReentrantLockTimeout implements Runnable {

    private static ReentrantLock lock = new ReentrantLock(false);

    @Override
    public void run() {
        try {
            //if (lock.tryLock(5, TimeUnit.SECONDS)) {
            if (lock.tryLock()) {
                Thread.sleep(6000);
            } else {
                System.out.println(Thread.currentThread().getName() + " 获取锁失败");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (lock.isHeldByCurrentThread()) {
                System.out.println(Thread.currentThread().getName() + " 释放锁");
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        ReentrantLockTimeout lockTimeout = new ReentrantLockTimeout();
        Thread t1 = new Thread(lockTimeout);
        Thread t2 = new Thread(lockTimeout);
        t1.start();
        t2.start();
    }
}
