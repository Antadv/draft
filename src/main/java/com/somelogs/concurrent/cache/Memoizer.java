package com.somelogs.concurrent.cache;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述
 * Created by liubingguang on 2017/8/17.
 */
public class Memoizer<A, V> implements Computable<A, V> {

    private Map<A, V> cache = new HashMap<>();
    private Computable<A, V> c;

    public Memoizer(Computable<A, V> c) {
        this.c = c;
    }

    @Override
    public synchronized V compute(A arg) {
        V result = cache.get(arg);
        if (result == null) {
            result = c.compute(arg);
        }
        cache.put(arg, result);
        return result;
    }
}
