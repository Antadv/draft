package gc;

/**
 * VM args: -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:PretenureSizeThreshold=3145728
 * Created by liubingguang on 2017/6/27.
 */
public class AllocationInOld {

    private static final int _1MB = 1024 * 1024;

    public static void main(String[] args) {
        byte[] alloaction;
        alloaction = new byte[4 * _1MB];
    }
}
