package com.somelogs.distribution.zookeeper;

import com.somelogs.utils.JsonUtils;
import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;

/**
 * 同步创建节点
 *
 * @author LBG - 2018/11/3 0003
 */
public class CreateNodeSync implements Watcher {

    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);

    /**
     * 注意：
     *      无论是同步接口还是异步接口，ZooKeeper 都不支持递归调用，
     *      即不存在父节点的情况下创建一个子节点，会抛出 KeeperException$NoNodeException 异常
     *      如果一个节点已存在，再创建相同的节点，会抛出 KeeperException$NodeExistsException 异常
     *
     * path         需要创建的数据节点路劲，例如 /zk-book/foo
     * data[]       一个字节数组，是节点创建后的初始内容
     * acl          节点的 ACL 策略，如果对权限没有太高的要求，直接用 Ids.OPEN_ACL_UNSAFE,
     *              表示之后对这个节点的任何操作都不受权限控制
     * createModel  节点类型，是一个枚举类型 {@link CreateMode}, 有 4 种可选的节点类型
     *              持久（PERSISTENT）
     *              持久顺序（PERSISTENT_SEQUENTIAL）
     *              临时（EPHEMERAL）临时节点不能创建子节点
     *              临时顺序（EPHEMERAL_SEQUENTIAL）
     */
    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        ZooKeeper zooKeeper = new ZooKeeper("127.0.0.1:2181", 5000, new CreateNodeSync());
        connectedSemaphore.await();

        String path = zooKeeper.create("/zk-test-ephemeral-", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        System.out.println("Success create node: " + path);

        String path2 = zooKeeper.create("/zk-test-ephemeral-", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println("Success create node: " + path2);

        Thread.sleep(Integer.MAX_VALUE);
    }

    @Override
    public void process(WatchedEvent event) {
        System.out.println("Receive watched event:" + JsonUtils.writeValueAsString(event));
        if (Objects.equals(Event.KeeperState.SyncConnected, event.getState())) {
            connectedSemaphore.countDown();
        }
    }

}
