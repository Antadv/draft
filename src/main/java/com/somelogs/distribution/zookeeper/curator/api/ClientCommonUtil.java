package com.somelogs.distribution.zookeeper.curator.api;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * 描述
 *
 * @author LBG - 2018/11/9 0009
 */
public class ClientCommonUtil {

    private ClientCommonUtil() {}

    public static CuratorFramework getClient() {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        return CuratorFrameworkFactory.builder().connectString("127.0.0.1:2181")
                                                .sessionTimeoutMs(5000)
                                                .retryPolicy(retryPolicy)
                                                .build();
    }
}
