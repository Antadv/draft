package com.somelogs.jvm.gc;

/**
 * -verbose:com.somelogs.jvm.gc
 * -Xms20M -Xmx20M
 * -Xmn10M
 * -XX:+PrintGCDetails
 * -XX:SurvivorRatio=8
 * -XX:MaxTenuringThreshold=1
 * -XX:+PrintTenuringDistribution
 *
 * Created by liubingguang on 2017/7/20.
 */
public class TestTenuringSThreshold {

    private static final int _1MB = 1024 * 1024;

    public static void main(String[] args) {
        byte[] allocation1, allocation2, allocation3;
        allocation1 = new byte[_1MB / 4];
        allocation2 = new byte[_1MB * 4];
        allocation3 = null;
        allocation3 = new byte[_1MB / 4];
    }
}
