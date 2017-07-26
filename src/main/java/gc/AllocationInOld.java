package gc;

/**
 * VM args: -verbose:gc
 * -Xms20M -Xmx20M
 * -Xmn10M
 * -XX:+PrintGCDetails
 * -XX:SurvivorRatio=8
 * -XX:+UserSerialGC
 * -XX:PretenureSizeThreshold=3145728
 *      大于这个设置值得对象直接在老年代分配（这个参数只对Serial和ParNew两款收集器有效，Parallel Scavenge 收集器不认识这个参数）
 *
 * Created by liubingguang on 2017/6/27.
 */
public class AllocationInOld {

    private static final int _1MB = 1024 * 1024;

    public static void main(String[] args) {
        byte[] alloaction;
        alloaction = new byte[4 * _1MB];
    }
}
