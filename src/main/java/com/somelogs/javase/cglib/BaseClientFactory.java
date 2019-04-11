package com.somelogs.javase.cglib;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.CallbackFilter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 描述
 * Created by liubingguang on 2017/8/11.
 */
public class BaseClientFactory {

    public <T> T create(Class<? extends T> clazz, Map<Method, Callback> callbackMap) {
        Callback[] callbacks = new Callback[callbackMap.size() + 1];
        int index = 0;
        callbacks[index++] = NoOp.INSTANCE;

        final Map<Method, Integer> methodIndexMap = new HashMap<>(callbackMap.size());
        for (Map.Entry<Method, Callback> entry : callbackMap.entrySet()) {
            callbacks[index] = entry.getValue();
            methodIndexMap.put(entry.getKey(), index++);
        }

        Enhancer enhancer = new Enhancer();
        enhancer.setCallbacks(callbacks);
        enhancer.setCallbackFilter(new CallbackFilter() {
            @Override
            public int accept(Method method) {
                Integer i = methodIndexMap.get(method);
                return i == null ? 0 : i;
            }
        });
        enhancer.setSuperclass(clazz);

        return clazz.cast(enhancer.create());
    }
}
