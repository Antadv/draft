package com.somelogs.zookeeper.curator.recipes;

import com.somelogs.zookeeper.curator.api.ClientCommonUtil;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.barriers.DistributedBarrier;
import org.apache.curator.framework.recipes.barriers.DistributedDoubleBarrier;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 分布式 Barrier
 *
 * @author LBG - 2018/11/11 0011
 */
public class DistributeBarrier {

    private static CyclicBarrier barrier = new CyclicBarrier(3);
    private static DistributedBarrier distributedBarrier;

    public static void main(String[] args) throws Exception {
        //jdkCycleBarrier();
        //distributedBarrier();
        distributedDoubleBarrier();
    }

    /**
     * Curator 实现分布式 Barrier
     */
    private static void distributedBarrier() throws Exception {
        final String path = "/curator_distributed_barrier";
        for (int i = 0; i < 5; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        CuratorFramework client = ClientCommonUtil.getClient();
                        client.start();
                        distributedBarrier = new DistributedBarrier(client, path);
                        System.out.println(Thread.currentThread().getName() + "号 barrier 设置");
                        distributedBarrier.setBarrier();
                        distributedBarrier.waitOnBarrier();
                        System.out.println("启动...");
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        Thread.sleep(2000);
        distributedBarrier.removeBarrier();
    }

    /**
     * Curator DistributedDoubleBarrier 实现分布式 Barrier
     */
    private static void distributedDoubleBarrier() {
        final String path = "/curator_distributed_barrier";
        for (int i = 0; i < 5; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        CuratorFramework client = ClientCommonUtil.getClient();
                        client.start();
                        DistributedDoubleBarrier doubleBarrier = new DistributedDoubleBarrier(client, path, 5);
                        Thread.sleep(Math.round(Math.random() * 3000));
                        System.out.println(Thread.currentThread().getName() + "号进入 barrier");
                        doubleBarrier.enter();
                        System.out.println("启动...");
                        Thread.sleep(Math.round(Math.random() * 3000));
                        doubleBarrier.leave();
                        System.out.println("退出...");
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    /**
     * jdk 自带的 CycleBarrier 实现赛跑比赛
     */
    private static void jdkCycleBarrier() {
        ExecutorService service = Executors.newFixedThreadPool(3);
        service.submit(new Runner("1 号选手"));
        service.submit(new Runner("2 号选手"));
        service.submit(new Runner("3 号选手"));
        service.shutdown();
    }

    static class Runner implements Runnable {
        private String name;

        public Runner(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            System.out.println(name + " 准备好了");
            try {
                barrier.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(name + " 起跑！");
        }
    }
}
