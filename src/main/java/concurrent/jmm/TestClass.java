package concurrent.jmm;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述
 *
 * @author LBG - 2017/10/27 0027
 */
public class TestClass {

    private int m;
    public int inc() {
        return m + 1;
    }

    public static void main(String[] args) {
        List<String> list = new ArrayList<>(2);
        list.add("33");
        System.out.println(list);
    }
}
