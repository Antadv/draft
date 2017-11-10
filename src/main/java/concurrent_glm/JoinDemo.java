package concurrent_glm;

/**
 * 面试题：有三个线程t1 t2 t3，如何让t1在t2前执行，t2在t3前执行
 * @author LBG - 2017/11/1 0001
 */
public class JoinDemo {

    public static void main(String[] args) {
        final Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("t1 start...");
            }
        });
        final Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    t1.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("t2 start...");
            }
        });
        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    t2.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("t3 start...");
            }
        });

        t2.start();
        t3.start();
        t1.start();
    }
}
