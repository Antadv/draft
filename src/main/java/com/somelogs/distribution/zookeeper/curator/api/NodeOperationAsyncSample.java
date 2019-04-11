package com.somelogs.distribution.zookeeper.curator.api;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.api.CuratorEventType;
import org.apache.zookeeper.CreateMode;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 异步使用 curator 创建节点
 *
 * @author LBG - 2018/11/9 0009
 */
public class NodeOperationAsyncSample {

    private static String path = "/zk-magic";
    private static ExecutorService executorService = Executors.newFixedThreadPool(2);
    private static CountDownLatch semaphore = new CountDownLatch(2);

    public static void main(String[] args) throws Exception {
        CuratorFramework client = ClientCommonUtil.getClient();
        client.start();
        System.out.println("main thread " + Thread.currentThread().getName());

        // 使用自定义线程池创建节点
        client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).inBackground(new BackgroundCallback() {
            @Override
            public void processResult(CuratorFramework curatorFramework, CuratorEvent curatorEvent) throws Exception {
                if (curatorEvent.getResultCode() == 0 &&curatorEvent.getType() == CuratorEventType.CREATE) {
                    System.out.println("thread name " + Thread.currentThread().getName());
                    System.out.println("create node " + curatorEvent.getPath());
                }
                semaphore.countDown();
            }
        }, executorService).forPath(path, "init".getBytes());

        // 没有使用自定义线程池，则会使用 Zookeeper 默认的 EventThread
        client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).inBackground(new BackgroundCallback() {
            @Override
            public void processResult(CuratorFramework curatorFramework, CuratorEvent curatorEvent) throws Exception {
                System.out.println("thread name " + Thread.currentThread().getName());
                System.out.println("result code " + curatorEvent.getResultCode());
                semaphore.countDown();
            }
        }).forPath(path, "init".getBytes());

        semaphore.await();
        executorService.shutdown();
    }
}
