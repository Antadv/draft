package cas;

/**
 * 描述
 * @author LBG - 2017/9/11 0011
 */
public class CASCounter {

    private static int value = 1;

    private synchronized static int compareAndSwapInt(int expectedValue,
                                                      int newValue) {
        int oldValue = value;
        System.out.println(value);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(value);
        if (expectedValue == oldValue) {
            value = newValue;
        }
        return oldValue;
    }

    private synchronized static boolean compareAndSet(int expectedValue,
                                                      int newValue) {
        return (expectedValue
                == compareAndSwapInt(expectedValue, newValue));
    }

    public static void main(String[] args) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                if (compareAndSet(1, 2)) {
                    System.out.println("set ok...");
                } else {
                    System.out.println("set fail...");
                }
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                value = 3;
            }
        });

        thread.start();
        thread2.start();
    }

}
