package concurrent_glm;

/**
 * wait()、notify()调用无同步块
 *
 * 抛出 IllegalMonitorStateException
 *
 * @author LBG - 2017/11/14 0014
 */
public class WaitAndNotifyWithoutSync {

    private static Object obj = new Object();

    private static class WaitThread implements Runnable {
        @Override
        public void run() {
            try {
                System.out.println(Thread.currentThread().getName() + " start!");
                obj.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Thread thread = new Thread(new WaitThread());
        thread.start();
    }
}
