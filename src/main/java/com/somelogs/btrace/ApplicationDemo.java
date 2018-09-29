package com.somelogs.btrace;

import java.util.concurrent.TimeUnit;

/**
 * 描述
 *
 * @author LBG - 2018/9/27 0027
 */
public class ApplicationDemo {

    public static void main(String[] args) throws InterruptedException {
        TimeUnit.SECONDS.sleep(15);
        add(1, 2);
    }

    public static void add(int a, int b) {
        System.out.println(1/0);
        System.out.println("a=" + a);
        System.out.println("b=" + b);
    }
}
