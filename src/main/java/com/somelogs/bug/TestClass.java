package com.somelogs.bug;

import java.io.Serializable;

/**
 * 描述
 *
 * @author LBG - 2018/1/18 0018
 */
public class TestClass implements Serializable {

    private static final long serialVersionUID = -4844427008339901477L;
    
    public static final String HELLO = "hello";
    public static int A = 232;
    public static final double B = 3.2;
    public static final long C = 23L;

    private volatile int m;

    public int add() throws IllegalArgumentException, NullPointerException {
        return this.m + 1;
    }

    public void set() {
        m = 1;
    }

    public int increase() {
        return m++;
    }

    public static void main(String[] args) {
        TestClass c = new TestClass();
        c.m = 2;
        System.out.println(c.increase());
    }
}
