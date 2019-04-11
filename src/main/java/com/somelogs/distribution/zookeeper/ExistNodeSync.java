package com.somelogs.distribution.zookeeper;

import org.apache.zookeeper.*;

import java.util.Objects;
import java.util.concurrent.CountDownLatch;

/**
 * 同步 API 检测节点是否存在
 *
 * @author LBG - 2018/11/9 0009
 */
public class ExistNodeSync implements Watcher {

    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
    private static ZooKeeper zooKeeper = null;

    public static void main(String[] args) throws Exception {
        zooKeeper = new ZooKeeper("127.0.0.1:2181", 5000, new ExistNodeSync());
        connectedSemaphore.await();

        String path = "/zk-data";
        zooKeeper.exists(path, true);
        zooKeeper.create(path, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        zooKeeper.setData(path, "123".getBytes(), -1);
        zooKeeper.create(path + "/c1", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);

        zooKeeper.delete(path + "/c1", -1);
        zooKeeper.delete(path, -1);

        Thread.sleep(Integer.MAX_VALUE);
    }

    /**
     * 1. 无论指定节点是否存在，通过调用 exists 接口都可以进行注册 Watcher
     * 2. exists 接口中注册的 Watcher，能够对节点创建、节点删除和节点数据更新事件进行监听
     * 3. 对于指定节点的子节点的各种变化，都不会通知客户端
     */
    @Override
    public void process(WatchedEvent event) {
        try {
            if (Objects.equals(Event.KeeperState.SyncConnected, event.getState())) {
                if (Event.EventType.None == event.getType() && event.getPath() == null) {
                    connectedSemaphore.countDown();
                } else if (Event.EventType.NodeCreated == event.getType()) {
                    System.out.println("Node " + event.getPath() + " created");
                    zooKeeper.exists(event.getPath(), true);
                } else if (Event.EventType.NodeDeleted == event.getType()) {
                    System.out.println("Node " + event.getPath() + " deleted");
                    zooKeeper.exists(event.getPath(), true);
                } else if (Event.EventType.NodeDataChanged == event.getType()) {
                    System.out.println("Node " + event.getPath() + " data changed");
                    zooKeeper.exists(event.getPath(), true);
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
