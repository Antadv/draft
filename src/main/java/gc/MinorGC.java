package gc;

/**
 * VM args: -verbose:gc
 *          -Xms20M -Xmx20M     堆大小为20MB，不可扩展
 *          -Xmn10M             新生代10MB
 *          -XX:+PrintGCDetails 打印GC日志
 *          -XX:SurvivorRatio=8 新生代中Eden区与一个Survivor 空间比例为8:1
 *          -XX:+UseSerialGC    使用 Serial + Serial Old 收集器组合
 */
public class MinorGC {

    private static final int _1MB = 1024 * 1024;

    public static void main(String[] args) {
        byte[] alloaction1, allocation2, allocation3, allocation4;
        alloaction1 = new byte[2 * _1MB];
        allocation2 = new byte[2 * _1MB];
        allocation3 = new byte[2 * _1MB];
        allocation4 = new byte[4 * _1MB];
    }
}

/**
 * 公司某个项目tomcat jvm 设置
 *
 * -server
 * -Xms2048m -Xmx2048m
 * -Xmn768m
 * -Xss256k 虚拟机栈大小
 * -XX:PermSize=128m -XX:MaxPermSize=256m
 * -XX:+UseConcMarkSweepGC
 * -XX:+UseParNewGC
 * -XX:CMSInitiatingOccupancyFraction=85
 * -XX:+UseCMSInitiatingOccupancyOnly
 * -XX:+ExplicitGCInvokesConcurrent
 * -XX:+UseCMSCompactAtFullCollection
 * -XX:CMSFullGCsBeforeCompaction=6
 * -XX:+HeapDumpOnOutOfMemoryError
 * -XX:HeapDumpPath=/usr/share/software/apache-tomcat/logs/oom.log
 * -XX:+PrintGCDetails
 * -XX:+PrintGCDateStamps
 */
