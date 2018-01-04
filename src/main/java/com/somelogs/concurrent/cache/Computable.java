package com.somelogs.concurrent.cache;

/**
 * 描述
 * Created by liubingguang on 2017/8/17.
 */
public interface Computable<A, V> {

    V compute(A arg);
}
