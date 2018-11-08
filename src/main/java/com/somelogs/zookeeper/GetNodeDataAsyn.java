package com.somelogs.zookeeper;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.util.concurrent.CountDownLatch;

/**
 * 异步 API 获取节点数据内容
 *
 * @author LBG - 2018/11/8 0008
 */
public class GetNodeDataAsyn implements Watcher {

    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
    private static ZooKeeper zooKeeper = null;
    private static Stat stat = new Stat();

    public static void main(String[] args) {

    }

    @Override
    public void process(WatchedEvent event) {

    }
}
