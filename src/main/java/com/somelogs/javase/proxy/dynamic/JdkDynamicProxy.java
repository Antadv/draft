package com.somelogs.javase.proxy.dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Objects;

/**
 * JDK 动态代理
 *
 * @author LBG - 2019/3/4
 */
public class JdkDynamicProxy {

    public static BookApi createJdkDynamicProxy(final BookApi delegate) {
        return (BookApi) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(),
                                                new Class[]{BookApi.class},
                                                new JdkHandler(delegate));
    }

    private static class JdkHandler implements InvocationHandler {

        final Object delegate;

        private JdkHandler(Object delegate) {
            this.delegate = delegate;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (Objects.equals(method.getName(), "sell")) {
                System.out.println("JDK dynamic proxy");
            }
            return null;
        }
    }
}
