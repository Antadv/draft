package com.somelogs.proxy.dynamic;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * 描述
 *
 * @author LBG - 2019/3/4
 */
public class CglibDynamicProxy {

    public static BookApi createCglibDynamicProxy(final BookApi delegate) {
        Enhancer enhancer = new Enhancer();
        enhancer.setCallback(new CglibInterceptor(delegate));
        enhancer.setInterfaces(new Class[]{BookApi.class});
        return (BookApi) enhancer.create();
    }

    private static class CglibInterceptor implements MethodInterceptor {

        final Object delegate;

        private CglibInterceptor(Object delegate) {
            this.delegate = delegate;
        }

        @Override
        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            if (Objects.equals(method.getName(), "sell")) {
                System.out.println("cglib dynamic proxy");
            }
            return null;
        }
    }
}
