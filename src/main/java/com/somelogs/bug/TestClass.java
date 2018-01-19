package com.somelogs.bug;

import java.io.Serializable;

/**
 * 描述
 *
 * @author LBG - 2018/1/18 0018
 */
public class TestClass implements Serializable {

    public static final String HELLO = "hello";
    public static int A = 232;
    public static final double B = 3.2;
    public static final long C = 23L;

    private int m;

    public int add()
    {
        return this.m + 1;
    }
}
