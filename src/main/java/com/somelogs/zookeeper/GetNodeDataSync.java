package com.somelogs.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.Objects;
import java.util.concurrent.CountDownLatch;

/**
 * 同步 API 获取节点数据内容
 *
 * @author LBG - 2018/11/8 0008
 */
public class GetNodeDataSync implements Watcher {

    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
    private static ZooKeeper zooKeeper = null;
    private static Stat stat = new Stat();

    public static void main(String[] args) throws Exception {
        zooKeeper = new ZooKeeper("127.0.0.1:2181", 5000, new GetNodeDataSync());
        connectedSemaphore.await();

        String path = "/zk-data";
        zooKeeper.create(path, "123".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        System.out.println(new String(zooKeeper.getData(path, true, stat)));
        System.out.println("stat hashcode " + stat.hashCode() + "," + stat.getCzxid() + "," + stat.getMzxid() + "," + stat.getVersion());

        zooKeeper.setData(path, "123".getBytes(), -1);

        Thread.sleep(Integer.MAX_VALUE);
    }

    /**
     * 节点的数据内容或是节点的数据版本的变化，都被看作是 Zookeeper 节点的变化
     */
    @Override
    public void process(WatchedEvent event) {
        if (Objects.equals(event.getState(), Event.KeeperState.SyncConnected)) {
            if (Objects.equals(Event.EventType.None, event.getType()) && event.getPath() == null) {
                connectedSemaphore.countDown();
            } else if (Objects.equals(event.getType(), Event.EventType.NodeDataChanged)) {
                try {
                    System.out.println(new String(zooKeeper.getData(event.getPath(), true, stat)));
                    System.out.println("stat hashcode " + stat.hashCode() + ","
                            + stat.getCzxid() + "," + stat.getMzxid() + "," + stat.getVersion() + "," + stat.getDataLength());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
