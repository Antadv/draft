package com.somelogs.client;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * 描述
 * Created by liubingguang on 2017/5/15.
 */
public class ProxyClass implements MethodInterceptor {


    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        ProxyAnnotation methodAnnotation = method.getAnnotation(ProxyAnnotation.class);
        ProxyAnnotation classAnnotation = method.getDeclaringClass().getAnnotation(ProxyAnnotation.class);

        StringBuilder builder = new StringBuilder();
        if (methodAnnotation != null) {
            builder.append(methodAnnotation.content());
        }
        if (classAnnotation != null) {
            builder.append(classAnnotation.content());
        }
        return builder.toString();
    }
}
