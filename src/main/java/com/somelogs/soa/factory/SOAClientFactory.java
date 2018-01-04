package com.somelogs.soa.factory;

import net.sf.cglib.proxy.Enhancer;
import com.somelogs.soa.proxy.ServerAccessor;

/**
 * Client Factory
 *
 * @author LBG - 2018/1/4 0004
 */
public class SOAClientFactory {

    private ServerAccessor ACCESSOR;

    public SOAClientFactory(String serverUrl) {
        ACCESSOR = new ServerAccessor(serverUrl);
    }

    public <T> T create(Class<T> clazz) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(ACCESSOR);
        return clazz.cast(enhancer.create());
    }
}
