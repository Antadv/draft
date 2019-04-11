package com.somelogs.distribution.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.Objects;
import java.util.concurrent.CountDownLatch;

/**
 * 同步 API 更新节点数据
 *
 * @author LBG - 2018/11/9 0009
 */
public class SetNodeDataSync implements Watcher {

    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);

    public static void main(String[] args) throws Exception {
        ZooKeeper zooKeeper = new ZooKeeper("127.0.0.1:2181", 5000, new SetNodeDataSync());
        connectedSemaphore.await();

        String path = "/zk-data";
        zooKeeper.create(path, "123".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        zooKeeper.getData(path, true, null);

        // -1 表示基于数据的最新版本进行更新
        // 如果对 Zookeeper 数据节点的更细操作没有原子性要求，那么就可以使用 -1
        Stat stat = zooKeeper.setData(path, "456".getBytes(), -1);
        System.out.println(stat.getCzxid() + ", " + stat.getMzxid() + ", " + stat.getVersion());

        Stat stat2 = zooKeeper.setData(path, "456".getBytes(), stat.getVersion());
        System.out.println(stat2.getCzxid() + ", " + stat2.getMzxid() + ", " + stat2.getVersion());

        try {
            zooKeeper.setData(path, "456".getBytes(), stat.getVersion());
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void process(WatchedEvent event) {
        if (Objects.equals(event.getState(), Event.KeeperState.SyncConnected)) {
            if (Objects.equals(event.getType(), Event.EventType.None) && event.getPath() == null) {
                connectedSemaphore.countDown();
            }
        }
    }
}
