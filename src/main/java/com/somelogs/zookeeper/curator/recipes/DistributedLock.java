package com.somelogs.zookeeper.curator.recipes;

import com.somelogs.zookeeper.curator.api.ClientCommonUtil;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

/**
 * 分布式锁
 *
 * @author LBG - 2018/11/11 0011
 */
public class DistributedLock {

    public static void main(String[] args) {
        String path = "/curator_distributed_lock";
        CuratorFramework client = ClientCommonUtil.getClient();
        client.start();

        final InterProcessMutex lock = new InterProcessMutex(client, path);
        final CountDownLatch latch = new CountDownLatch(1);
        for (int i = 0; i < 30; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        latch.await();
                        lock.acquire();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss|SSS");
                        System.out.println("生成的订单号是：" + dateFormat.format(new Date()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        lock.release();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        latch.countDown();
    }
}
