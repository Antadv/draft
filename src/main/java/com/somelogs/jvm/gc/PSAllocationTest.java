package com.somelogs.jvm.gc;

/**
 * -Xmx100M                         最大堆
 * -XX:+PrintGCDetails              打印GC日志
 * -XX:MaxNewSize=40M               最大新生代
 * -XX:+PrintTenuringDistribution   输出幸存区年龄分布
 *
 * Created by liubingguang on 2017/7/20.
 */
public class PSAllocationTest {

    private static final int _1MB = 1024 * 1024;

    public static void main(String[] args) {
        byte[] byte1 = new byte[_1MB * 5];
        byte[] byte2 = new byte[_1MB * 10];
        byte1 = null;
        byte2 = null;
        byte[] byte3 = new byte[_1MB * 5];
        byte[] byte4 = new byte[_1MB * 10];
        byte3 = null;
        byte4 = null;
        byte[] byte5 = new byte[_1MB * 15];
    }
}
