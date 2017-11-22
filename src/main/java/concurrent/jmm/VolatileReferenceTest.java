package concurrent.jmm;

/**
 * volatile 修饰引用类型
 *
 * @author LBG - 2017/11/22 0022
 */
public class VolatileReferenceTest implements Runnable {

    private static class InnerClass {
        private boolean flag = true;
    }

    // 去掉 volatile, 程序将不会结束
    private volatile InnerClass inner;

    public VolatileReferenceTest(InnerClass inner) {
        this.inner = inner;
    }

    private void stop() {
        inner.flag = false;
    }

    @Override
    public void run() {
        System.out.println("--" + inner.flag);
        // 想要达到程序不结束的效果, 保持循环体为空
        while (inner.flag)
            ;
    }

    public static void main(String[] args) throws InterruptedException {
        VolatileReferenceTest test = new VolatileReferenceTest(new InnerClass());
        Thread thread = new Thread(test);
        thread.start();

        Thread.sleep(1000L);
        test.stop();
        thread.join();
    }
}
