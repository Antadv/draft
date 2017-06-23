package oom;

import java.util.ArrayList;
import java.util.List;

/**
 * 运行时常量池溢出
 * VM Args: -XX:PermSize=10M -XX:MaxPermSize=10M
 *
 * Created by liubingguang on 2017/6/23.
 */
public class RuntimeConstantPoolOOM {

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        int i = 0;
        while (true) {
            list.add(String.valueOf(i++).intern());
        }
    }
}
