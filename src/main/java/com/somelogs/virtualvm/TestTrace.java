package com.somelogs.virtualvm;

import java.io.IOException;

/**
 * 描述
 * Created by liubingguang on 2017/7/17.
 */
public class TestTrace {

    public int add(int a, int b) {
        return a + b;
    }

    public static void main(String[] args) throws IOException {
        //TestTrace test = new TestTrace();
        //BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        //
        //for (int i = 0; i < 1000; i++) {
        //    reader.readLine();
        //    int a = (int) Math.round(Math.random() * 1000);
        //    int b = (int) Math.round(Math.random() * 1000);
        //    System.out.println(test.add(a, b));
        //}

        int cpuCount = Runtime.getRuntime().availableProcessors();
        System.out.println("cpu count = " + cpuCount);
    }
}
