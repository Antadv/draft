package com.somelogs.distribution.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;

/**
 * 使用异步 API 获取获取子节点列表
 *
 * @author LBG - 2018/11/7 0007
 */
public class GetChildrenAsync implements Watcher {

    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
    private static ZooKeeper zooKeeper = null;

    public static void main(String[] args) throws Exception {
        zooKeeper = new ZooKeeper("127.0.0.1:2181", 5000, new GetChildrenAsync());
        connectedSemaphore.await();

        String path = "/zk-book";
        //zooKeeper.create(path, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        zooKeeper.create(path + "/c1", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        zooKeeper.getChildren(path, true, new IChildren2CallBack(), "create children c1");

        zooKeeper.create(path + "/c2", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        zooKeeper.getChildren(path, true, new IChildren2CallBack(), "create children c2");

        Thread.sleep(Integer.MAX_VALUE);
    }

    @Override
    public void process(WatchedEvent event) {
        if (Objects.equals(Event.KeeperState.SyncConnected, event.getState())) {
            if (Objects.equals(Event.EventType.None, event.getType()) && event.getPath() == null) {
                connectedSemaphore.countDown();
            } else if (Objects.equals(event.getType(), Event.EventType.NodeChildrenChanged)) {
                try {
                    System.out.println("ReGet Child:" + zooKeeper.getChildren(event.getPath(), true));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 异步获取数据接口通常会应用在这样的场景中：
     * 应用启动的时候，会获取一些配置信息，例如“机器列表”，
     * 这些配置通常比较大，并且不希望配置的获取影响应用的主流程。
     */
    static class IChildren2CallBack implements AsyncCallback.Children2Callback {
        @Override
        public void processResult(int rc, String path, Object ctx, List<String> children, Stat stat) {
            System.out.println("rc:" + rc + ", path:" + path + ", ctx:" + ctx);
            System.out.println("children:" + children);
            System.out.println("stat:" + stat);
        }
    }
}
