package com.somelogs.zookeeper;

import com.somelogs.utils.JsonUtils;
import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import java.util.Objects;
import java.util.concurrent.CountDownLatch;

/**
 * 描述
 *
 * @author LBG - 2018/11/3 0003
 */
public class CreateNodeAsyn implements Watcher {

    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);

    public static void main(String[] args) {

    }

    @Override
    public void process(WatchedEvent event) {
        System.out.println("Receive watched event:" + JsonUtils.object2JSONString(event));
        if (Objects.equals(Event.KeeperState.SyncConnected, event.getState())) {
            connectedSemaphore.countDown();
        }
    }

    class IStringCallback implements AsyncCallback.StringCallback {
        @Override
        public void processResult(int rc, String path, Object ctx, String name) {
            System.out.println("rc: " + rc + ", path: " + path + ", ctx: " + ctx + ", name: " + name);
        }
    }
}
