package com.somelogs.btrace;

import java.util.concurrent.TimeUnit;

/**
 * 描述
 *
 * @author LBG - 2018/9/27 0027
 */
public class ApplicationDemo {

    public static void main(String[] args) throws InterruptedException {
        int a = 0;
        int b = 0;
        while (true) {
            System.out.println("result:" + add(a, b));
            a++;
            b++;
            TimeUnit.SECONDS.sleep(2);
        }
    }

    public static int add(int a, int b) {
        return a + b;
    }
}
