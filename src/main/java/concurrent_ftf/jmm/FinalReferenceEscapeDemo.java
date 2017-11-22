package concurrent_ftf.jmm;

/**
 * 描述
 *
 * @author LBG - 2017/11/21 0021
 */
public class FinalReferenceEscapeDemo {

    final int i;
    static FinalReferenceEscapeDemo obj;

    public FinalReferenceEscapeDemo() {
        i = 1;                      // 1 写 final 域
        obj = this;                 // 2 this 在此”逃逸“
    }

    public static void write() {
        new FinalReferenceEscapeDemo();
    }

    public static int read() {
        if (obj != null) {          // 3
            return obj.i;           // 4
        }
        return -1;
    }

    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int temp = FinalReferenceEscapeDemo.read();
                while (temp != 1) {
                    System.out.println("temp: " + temp);
                }
            }
        }).start();

        FinalReferenceEscapeDemo.write();
    }
}
