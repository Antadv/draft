package com.somelogs.distribution.zookeeper.curator.recipes;

import com.somelogs.distribution.zookeeper.curator.api.ClientCommonUtil;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.atomic.AtomicValue;
import org.apache.curator.framework.recipes.atomic.DistributedAtomicInteger;
import org.apache.curator.retry.RetryNTimes;

/**
 * 分布式计数器
 * 典型场景是统计系统的在线人数
 *
 * @author LBG - 2018/11/11 0011
 */
public class DistributedInteger {

    public static void main(String[] args) throws Exception {
        String path = "/curator_distributed_integer";
        CuratorFramework client = ClientCommonUtil.getClient();
        client.start();

        DistributedAtomicInteger atomicInteger = new DistributedAtomicInteger(client, path, new RetryNTimes(3, 1000));
        AtomicValue<Integer> rc = atomicInteger.add(8);
        System.out.println("result: " + rc.succeeded());
    }
}
