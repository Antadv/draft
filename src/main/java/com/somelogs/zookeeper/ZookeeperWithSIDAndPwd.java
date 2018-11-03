package com.somelogs.zookeeper;

import com.somelogs.utils.JsonUtils;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;

/**
 * 复用 sessionId, Pwd 的构造
 *
 * @author LBG - 2018/11/3 0003
 */
public class ZookeeperWithSIDAndPwd implements Watcher {

    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);

    public static void main(String[] args) throws IOException, InterruptedException {
        ZooKeeper zooKeeper = new ZooKeeper("127.0.0.1:2181", 5000, new ZookeeperWithSIDAndPwd());
        connectedSemaphore.await();

        long sessionId = zooKeeper.getSessionId();
        byte[] pwd = zooKeeper.getSessionPasswd();

        // use wrong session id and pwd
        zooKeeper = new ZooKeeper("127.0.0.1:2181", 5000, new ZookeeperWithSIDAndPwd(), 2L, "test".getBytes());
        // use correct session id and pwd
        zooKeeper = new ZooKeeper("127.0.0.1:2181", 5000, new ZookeeperWithSIDAndPwd(), sessionId, pwd);

        Thread.sleep(Integer.MAX_VALUE);
    }

    @Override
    public void process(WatchedEvent event) {
        System.out.println("Receive watched event:" + JsonUtils.object2JSONString(event));
            if (Objects.equals(Event.KeeperState.SyncConnected, event.getState())) {
            connectedSemaphore.countDown();
        }
    }

}
