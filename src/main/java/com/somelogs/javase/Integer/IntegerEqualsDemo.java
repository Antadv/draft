package com.somelogs.javase.Integer;

import java.util.Objects;

/**
 * 描述
 *
 * @author LBG - 2018/10/24 0024
 */
public class IntegerEqualsDemo {

    public static void main(String[] args) {
        Integer a = 300;
        Integer b = 300;
        System.out.println(a == b); // false
        System.out.println(a.equals(b)); // true

        Integer c = 300;
        int d = 300;
        System.out.println(c == d); // true
        System.out.println(c.equals(d)); // true

        Integer e = null;
        int f = 300;
        //System.out.println(e == f); // exp
        //System.out.println(e.equals(f)); // exp
        System.out.println(Objects.equals(e, f)); // false
    }
}
