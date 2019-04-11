package com.somelogs.distribution.zookeeper;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.util.Objects;
import java.util.concurrent.CountDownLatch;

/**
 * 删除节点
 *
 * @author LBG - 2018/11/7 0007
 */
public class DeleteNode implements Watcher {

    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);

    public static void main(String[] args) throws Exception {
        ZooKeeper zooKeeper = new ZooKeeper("127.0.0.1:2181", 5000, new DeleteNode());
        connectedSemaphore.await();

        zooKeeper.delete("/zk-test-ephemeral-", 0);
        zooKeeper.delete("/zk-test-ephemeral-0000000015", 0, new IVoidCallBack(), "i am ctx");
    }

    @Override
    public void process(WatchedEvent event) {
        if (Objects.equals(Event.KeeperState.SyncConnected, event.getState())) {
            connectedSemaphore.countDown();
        }
    }

    // 异步回调
    static class IVoidCallBack implements AsyncCallback.VoidCallback {
        @Override
        public void processResult(int rc, String path, Object ctx) {
            System.out.println("rc:" + rc + ", path:" + path + ", ctx:" + ctx);
        }
    }
}
