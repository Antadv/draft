package com.somelogs.jvm.jvm_zzm.classloader_74;

/**
 * 描述
 *
 * @author LBG - 2018/3/15 0015
 */
public class ClassLoaderDemo {

    public static void main(String[] args) {
        ClassLoader appClassLoader = ClassLoaderDemo.class.getClassLoader();
        ClassLoader extAppClassLoader = appClassLoader.getParent();
        ClassLoader bstClassLoader = extAppClassLoader.getParent();

        System.out.println(appClassLoader);
        System.out.println(extAppClassLoader);
        System.out.println(bstClassLoader);
    }
}
