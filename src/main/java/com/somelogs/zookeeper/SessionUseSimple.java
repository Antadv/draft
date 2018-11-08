package com.somelogs.zookeeper;

import com.somelogs.utils.JsonUtils;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;

/**
 * 简单构造
 *
 * @author LBG - 2018/11/3 0003
 */
public class SessionUseSimple implements Watcher {

    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);

    public static void main(String[] args) throws IOException {
        ZooKeeper zooKeeper = new ZooKeeper("127.0.0.1:2181", 5000, new SessionUseSimple());

        /*
         * Zookeeper 客户端和服务器会话的建立是一个异步的过程，
         * 也就是说，构造方法会在处理完客户端初始化工作后立即返回，
         * 在大多数情况下，此时并没有真正建立好一个可用的会话，
         * 在会话的生命周期中处于 "CONNECTING" 的状态
         *
         * 所以，这里会打印 CONNECTING
         */
        System.out.println(zooKeeper.getState());

        try {
            connectedSemaphore.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Zookeeper session established");
    }

    @Override
    public void process(WatchedEvent event) {
        System.out.println("Receive watched event:" + JsonUtils.writeValueAsString(event));
        if (Objects.equals(Event.KeeperState.SyncConnected, event.getState())) {
            connectedSemaphore.countDown();
        }
    }
}
