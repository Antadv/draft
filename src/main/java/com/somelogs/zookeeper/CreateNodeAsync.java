package com.somelogs.zookeeper;

import com.somelogs.utils.JsonUtils;
import org.apache.zookeeper.*;

import java.util.Objects;
import java.util.concurrent.CountDownLatch;

/**
 * 描述
 *
 * @author LBG - 2018/11/3 0003
 */
public class CreateNodeAsync implements Watcher {

    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);

    public static void main(String[] args) throws Exception {
        ZooKeeper zooKeeper = new ZooKeeper("127.0.0.1:2181", 5000, new CreateNodeAsync());
        connectedSemaphore.await();

        zooKeeper.create("/zk-test-ephemeral-", "".getBytes(),
                         ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL,
                         new IStringCallback(), "I am context 1");

        zooKeeper.create("/zk-test-ephemeral-", "".getBytes(),
                         ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL,
                         new IStringCallback(), "I am context 2");

        zooKeeper.create("/zk-test-ephemeral-", "".getBytes(),
                         ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL,
                         new IStringCallback(), "I am context 3");

        Thread.sleep(Integer.MAX_VALUE);
    }

    @Override
    public void process(WatchedEvent event) {
        System.out.println("Receive watched event:" + JsonUtils.writeValueAsString(event));
        if (Objects.equals(Event.KeeperState.SyncConnected, event.getState())) {
            connectedSemaphore.countDown();
        }
    }

    /**
     * rc   return code
     *      0（OK）接口调用成功
     *      -4（ConnectionLoss）客户端合同服务端连接已断开
     *      -110（NodeExists）指定节点已存在
     *      -112（SessionExpired）会话已过期
     *
     * path 调接口时传入 API 的数据节点的节点路劲参数值
     * ctx  接口调用时传入 API 的 ctx 参数值
     * name 实际在服务端创建的节点名
     */
    static class IStringCallback implements AsyncCallback.StringCallback {
        @Override
        public void processResult(int rc, String path, Object ctx, String name) {
            System.out.println("rc: " + rc + ", path: " + path + ", ctx: " + ctx + ", name: " + name);
        }
    }
}
