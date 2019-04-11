package com.somelogs.distribution.client;

import net.sf.cglib.proxy.Enhancer;

/**
 * 描述
 * Created by liubingguang on 2017/5/15.
 */
public class ProxyFactory {

    private static final ProxyClass PROXY_CLASS = new ProxyClass();

    public Object create(Class clazz) {
        Enhancer enhancer = new Enhancer();
        enhancer.setCallback(PROXY_CLASS);
        enhancer.setSuperclass(clazz);
        return enhancer.create();
    }
}


