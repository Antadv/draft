package com.somelogs.concurrent_glm;

/**
 * 等待和通知
 * @see Object#wait()       等待
 * @see Object#notify()     等待队列中随机唤醒一个
 * @see Object#notifyAll()  唤醒所有
 *
 * <p>wait() notify() 执行前都必须对象的锁</p>
 *
 * @author LBG - 2017/9/21 0021
 */
public class WaitAndNotify {

    private static Object obj = new Object();

    public static class WaitThread extends Thread {
        @Override
        public void run() {
            synchronized (obj) {
                System.out.println(System.currentTimeMillis() + " T1 start");
                try {
                    obj.wait();
                    System.out.println(System.currentTimeMillis() + " T1 notified");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static class NotifyThread extends Thread {
        @Override
        public void run() {
            synchronized (obj) {
                System.out.println(System.currentTimeMillis() + " T2 start");
                obj.notify();

                // 注释下面代码，观察T2 start时间和T1 notified时间
                // 下面代码主要是为了演示 obj.notify() 后 t1 虽然被唤醒了，但是并不能立即继续执行
                // 因为 t2 还没执行完，还没释放锁，t1 必须获得锁才能继续执行
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        Thread t1 = new WaitThread();
        Thread t2 = new NotifyThread();
        t1.start();
        t2.start();
    }
}
