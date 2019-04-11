package com.somelogs.javase.proxy.dynamic;

/**
 * 描述
 *
 * @author LBG - 2019/3/4
 */
public class DynamicProxyTest {

    public static void main(String[] args) throws Exception {
        BookApi delegate = new BookApiImpl();

        // JDK 动态代理
        BookApi jdkDynamicProxy = JdkDynamicProxy.createJdkDynamicProxy(delegate);
        jdkDynamicProxy.sell();

        // Cglib 动态代理
        BookApi cglibDynamicProxy = CglibDynamicProxy.createCglibDynamicProxy(delegate);
        cglibDynamicProxy.sell();

        // Javassist 直接生成代理对象
        BookApi bytecodeDynamicProxy = JavassistDynamicProxy.createJavassistBytecodeDynamicProxy();
        bytecodeDynamicProxy.sell();
    }
}
