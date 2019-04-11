package com.somelogs.distribution.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.Objects;
import java.util.concurrent.CountDownLatch;

/**
 * 异步 API 获取节点数据内容
 *
 * @author LBG - 2018/11/8 0008
 */
public class GetNodeDataAsync implements Watcher {

    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
    private static ZooKeeper zooKeeper = null;

    public static void main(String[] args) throws Exception {
        zooKeeper = new ZooKeeper("127.0.0.1:2181", 5000, new GetNodeDataAsync());
        connectedSemaphore.await();

        String path = "/zk-data";
        zooKeeper.create(path, "123".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        zooKeeper.getData(path, true, new IDataCallBack(), "get data...");
        zooKeeper.setData(path, "123".getBytes(), -1);

        Thread.sleep(Integer.MAX_VALUE);
    }

    @Override
    public void process(WatchedEvent event) {
        if (Objects.equals(event.getState(), Event.KeeperState.SyncConnected)) {
            if (Objects.equals(Event.EventType.None, event.getType()) && event.getPath() == null) {
                connectedSemaphore.countDown();
            } else if (Objects.equals(event.getType(), Event.EventType.NodeDataChanged)) {
                try {
                    zooKeeper.getData(event.getPath(), true, new IDataCallBack(), "node data changed...");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class IDataCallBack implements AsyncCallback.DataCallback {
        @Override
        public void processResult(int rc, String path, Object ctx, byte[] data, Stat stat) {
            System.out.println("rc:" + rc + ", path:" + path + ", ctx:" + ctx + ", data:" + new String(data));
            System.out.println("stat:" + stat);
        }
    }
}
