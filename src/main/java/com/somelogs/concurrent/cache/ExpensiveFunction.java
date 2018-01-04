package com.somelogs.concurrent.cache;

/**
 * 描述
 * Created by liubingguang on 2017/8/17.
 */
public class ExpensiveFunction implements Computable<String, Integer> {

    @Override
    public Integer compute(String arg) {
        return Integer.valueOf(arg);
    }
}
