package com.somelogs.jvm_zzm.classloader_72;

/**
 * 描述
 *
 * @author LBG - 2018/3/5 0005
 */
public class NotInitialization {

    public static void main(String[] args) {
        /*
         * 通过子类来引用父类中定义的静态字段，
         * 只会触发父类的初始化而不会触发子类的初始化
         */
        //System.out.println(SubClass.value);

        /*
         * 数组不会触发类的初始化
         */
        //SuperClass[] aca = new SuperClass[10];

        System.out.println(ConstClass.VALUE);
    }
}
