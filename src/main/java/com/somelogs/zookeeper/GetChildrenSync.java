package com.somelogs.zookeeper;

import org.apache.zookeeper.*;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;

/**
 * 使用同步 API 获取获取子节点列表
 *
 * @author LBG - 2018/11/7 0007
 */
public class GetChildrenSync implements Watcher {

    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
    private static ZooKeeper zooKeeper = null;

    public static void main(String[] args) throws Exception {
        zooKeeper = new ZooKeeper("127.0.0.1:2181", 5000, new GetChildrenSync());
        connectedSemaphore.await();

        String path = "/zk-book";
        zooKeeper.create(path, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        zooKeeper.create(path + "/c1", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);

        List<String> childrenList = zooKeeper.getChildren(path, true);
        System.out.println(childrenList);
        zooKeeper.create(path + "/c2", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);

        Thread.sleep(Integer.MAX_VALUE);
    }

    /**
     * 关于 Watcher，这里简单提一点，
     * Zookeeper 服务端在向客户端发送 Watcher “NodeChildrenChanged” 事件通知的时候，
     * 仅仅只会发出一个通知，而不会把节点的变化情况发送给客户端，需要客户端自己重新获取。
     *
     * 另外，由于 Watcher 通知是一次性的，即一旦触发一次通知后，该 Watcher 就失效了，
     * 因此客户端需要反复注册 Watcher
     */
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
}
