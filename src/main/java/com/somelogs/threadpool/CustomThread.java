package com.somelogs.threadpool;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 自定义线程
 * @author LBG - 2017/9/8 0008
 */
public class CustomThread extends Thread {

    private static final String DEFAULT_NAME = "MyBrokerThread";
    private static final AtomicInteger created = new AtomicInteger();

    public CustomThread(Runnable target) {
        this(target, DEFAULT_NAME);
    }

    public CustomThread(Runnable target, String name) {
        super(target, name + "-" + created.incrementAndGet());
    }
}
