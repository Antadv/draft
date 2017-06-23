package classloader.load;

/**
 * 描述
 * Created by liubingguang on 2017/6/21.
 */
public class ParentClass {

    static {
        System.out.println("parent class");
    }

    public static final int value = 2;
}
