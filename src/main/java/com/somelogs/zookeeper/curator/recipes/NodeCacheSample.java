package com.somelogs.zookeeper.curator.recipes;

import com.somelogs.zookeeper.curator.api.ClientCommonUtil;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.zookeeper.CreateMode;

/**
 * 事件监听
 *
 * @author LBG - 2018/11/9 0009
 */
public class NodeCacheSample {

    public static void main(String[] args) throws Exception {
        CuratorFramework client = ClientCommonUtil.getClient();
        client.start();
        childrenNodeCacheListen(client);
    }

    /**
     * 事件监听
     * 不仅可以监听节点数据内容的变化，也能监听指定节点是否存在
     */
    private static void nodeCacheListen(CuratorFramework client) throws Exception {
        String path = "/zk-book/node-cache";
        client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(path, "init".getBytes());
        final NodeCache nodeCache = new NodeCache(client, path, false);
        // 设置为 true, 则 NodeCache 在第一次启动的时候就会立刻从 Zookeeper 上读取对应节点数据，保存在 Cache 中
        nodeCache.start(true);
        nodeCache.getListenable().addListener(new NodeCacheListener() {
            @Override
            public void nodeChanged() throws Exception {
                System.out.println("Node data update, new data: " + new String(nodeCache.getCurrentData().getData()));
            }
        });
        client.setData().forPath(path, "update".getBytes());
        Thread.sleep(1000);
        // 如果节点被删除，那么 Curator 就无法触发 NodeCacheListener
        client.delete().deletingChildrenIfNeeded().forPath(path);
        Thread.sleep(Integer.MAX_VALUE);
    }

    /**
     * 子节点事件监听
     * Curator 无法对二级节点进行事件监听
     */
    private static void childrenNodeCacheListen(CuratorFramework client) throws Exception {
        String path = "/zk-list";
        final PathChildrenCache cache = new PathChildrenCache(client, path, true);
        cache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);
        cache.getListenable().addListener(new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
                switch (event.getType()) {
                    case CHILD_ADDED:
                        System.out.println("CHILD_ADDED " + event.getData().getPath());
                        break;
                    case CHILD_UPDATED:
                        System.out.println("CHILD_UPDATED " + event.getData().getPath());
                        break;
                    case CHILD_REMOVED:
                        System.out.println("CHILD_REMOVED " + event.getData().getPath());
                        break;
                    default:
                        break;
                }
            }
        });
        client.create().withMode(CreateMode.PERSISTENT).forPath(path);
        Thread.sleep(1000);

        client.create().withMode(CreateMode.EPHEMERAL).forPath(path + "/c1");
        Thread.sleep(1000);
        client.delete().forPath(path + "/c1");
        Thread.sleep(1000);
        client.delete().forPath(path);
        Thread.sleep(Integer.MAX_VALUE);
    }
}
