package com.somelogs.jvm_zzm.classloader_72;

/**
 * 描述
 *
 * @author LBG - 2018/3/5 0005
 */
public class ConstClass {

    static {
        System.out.println("ConstClass init");
    }

    public static final String VALUE = "hello world";
}
