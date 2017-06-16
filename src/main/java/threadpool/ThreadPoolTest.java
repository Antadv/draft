package threadpool;

/**
 * 描述
 * Created by liubingguang on 2017/6/14.
 */
public class ThreadPoolTest {

    public static void main(String[] args) throws InterruptedException {
        Thread.sleep(3000);
        test();
        System.out.println(Runtime.getRuntime().freeMemory());
    }

    public static void test() {
        for (int i = 0; i < 1000000; i++) {
            new Thread("thread " + i) {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName());
                }
            }.start();
        }

    }
}
