package com.somelogs.zookeeper.curator.api;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * 使用 curator 创建会话
 *
 * @author LBG - 2018/11/9 0009
 */
public class CreateSessionSample {

    public static void main(String[] args) throws Exception {
        createSessionFluent();
    }

    private static void createSession() throws Exception {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.newClient("127.0.0.1:2181", 5000, 3000, retryPolicy);
        client.start();
        Thread.sleep(Integer.MAX_VALUE);
    }

    private static void createSessionFluent() throws Exception {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.builder().connectString("127.0.0.1:2181")
                                                                   .sessionTimeoutMs(5000)
                                                                   .retryPolicy(retryPolicy)
                                                                   .build();
        client.start();
        Thread.sleep(Integer.MAX_VALUE);
    }
}
