package concurrent_glm;

/**
 * 等待和通知
 * @see Object#wait()       等待
 * @see Object#notify()     等待队列中随机唤醒一个
 * @see Object#notifyAll()  唤醒所有
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
