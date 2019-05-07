package com.somelogs.javase.binary;

/**
 * Java 常见的运算符 &（与） ~（非） |（或） ^（异或）
 *
 * @author LBG - 2019/5/6
 */
public class OperatorDemo {

    public static void main(String[] args) {
        unTest();
    }

    /**
     * &：两位都是1才为1
     */
    private static void andTest() {
        int a = 65;
        int b = 234;
        System.out.println(Integer.toBinaryString(a));
        System.out.println(Integer.toBinaryString(b));
        System.out.println(a & b);
    }

    /**
     * |：只要一位为1则为1
     */
    private static void orTest() {
        int a = 65;
        int b = 234;
        System.out.println(Integer.toBinaryString(a));
        System.out.println(Integer.toBinaryString(b));
        System.out.println(a | b);
    }

    private static void unTest() {
        int a = 65;
        System.out.println(Integer.toBinaryString(a));
        System.out.println(Integer.toBinaryString(~a));
        System.out.println(~a);
    }

    /**
     * ^：异或，相同为0，否则为1
     */
    private static void unOrTest() {
        int a = 65;
        int b = 234;
        System.out.println(Integer.toBinaryString(a));
        System.out.println(Integer.toBinaryString(b));
        System.out.println(a ^ b);
    }
}
