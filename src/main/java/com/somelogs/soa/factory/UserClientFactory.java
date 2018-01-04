package com.somelogs.soa.factory;

import net.sf.cglib.proxy.Enhancer;
import com.somelogs.soa.proxy.ServerAccessor;

/**
 * Client Factory
 *
 * @author LBG - 2018/1/4 0004
 */
public class UserClientFactory {

    private static final ServerAccessor ACCESSOR = new ServerAccessor();

    public <T> T create(Class<T> clazz) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(ACCESSOR);
        return clazz.cast(enhancer.create());
    }
}
