package concurrent_glm;

/**
 * @see Thread#join()       无限等待线程结束
 * @see Thread#join(long)   指定时间内没有执行结束，当前线程继续往下执行
 *
 * @see Thread#yield()      当前线程让出CPU，但还会进行CPU 资源的竞争
 *
 * @author LBG - 2017/9/21 0021
 */
public class JoinAndYield {

    private static volatile int i = 0;

    public static class AddThread extends Thread {
        @Override
        public void run() {
            for (i = 0; i < 10000; i++) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t = new AddThread();
        t.start();
        t.join();
        //t.join(500);
        System.out.println(i);
    }
}
