package com.somelogs.distribution.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.Objects;
import java.util.concurrent.CountDownLatch;

/**
 * 异步 API 更新节点数据
 *
 * @author LBG - 2018/11/9 0009
 */
public class SetNodeDataAsync implements Watcher {

    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);

    public static void main(String[] args) throws Exception {
        ZooKeeper zooKeeper = new ZooKeeper("127.0.0.1:2181", 5000, new SetNodeDataAsync());
        connectedSemaphore.await();

        String path = "/zk-data";
        zooKeeper.create(path, "123".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        zooKeeper.setData(path, "456".getBytes(), -1, new IStatCallBack(), null);

        Thread.sleep(Integer.MAX_VALUE);
    }

    @Override
    public void process(WatchedEvent event) {
        if (Objects.equals(event.getState(), Event.KeeperState.SyncConnected)) {
            if (Objects.equals(event.getType(), Event.EventType.None) && event.getPath() == null) {
                connectedSemaphore.countDown();
            }
        }
    }

    static class IStatCallBack implements AsyncCallback.StatCallback {
        @Override
        public void processResult(int rc, String path, Object ctx, Stat stat) {
            if (rc == 0) {
                System.out.println("SUCCESS");
            }
        }
    }
}
