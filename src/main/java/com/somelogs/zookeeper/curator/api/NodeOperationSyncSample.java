package com.somelogs.zookeeper.curator.api;

import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

/**
 * 同步使用 curator 创建节点
 *
 * @author LBG - 2018/11/9 0009
 */
public class NodeOperationSyncSample {

    private static String path = "/zk-book/c1";

    public static void main(String[] args) throws Exception {
        CuratorFramework client = ClientCommonUtil.getClient();
        client.start();
        setData(client);
    }

    /**
     * 创建节点
     * creatingParentsIfNeeded 递归创建父节点，父节点为持久节点，当前节点为临时节点
     */
    private static void createNode(CuratorFramework client) throws Exception {
        client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(path, "init".getBytes());
    }

    /**
     * 删除节点
     * deletingChildrenIfNeeded 递归删除其所有节点
     *
     * 保证节点一定会会删除成功
     * client.delete().guaranteed().forPath(path);
     */
    private static void deleteNode(CuratorFramework client) throws Exception {
        createNode(client);
        Stat stat = new Stat();
        client.getData().storingStatIn(stat).forPath(path);
        System.out.println(stat.getCzxid() + ", " + stat.getMzxid() + ", " + stat.getVersion());

        client.delete().deletingChildrenIfNeeded().withVersion(stat.getVersion()).forPath(path);
    }

    /**
     * 读取节点数据
     */
    private static void getData(CuratorFramework client) throws Exception {
        createNode(client);
        Stat stat = new Stat();
        System.out.println(new String(client.getData().storingStatIn(stat).forPath(path)));
    }

    /**
     * 更新节点数据
     */
    private static void setData(CuratorFramework client) throws Exception {
        createNode(client);
        Stat stat = new Stat();
        client.getData().storingStatIn(stat).forPath(path);
        Stat stat2 = client.setData().withVersion(stat.getVersion()).forPath(path, "update".getBytes());
        System.out.println("Success update node data, new version " + stat2.getVersion());

        try {
            client.setData().withVersion(stat.getVersion()).forPath(path);
        } catch(Exception e) {
            System.out.println("Fail to update node data" + e.getMessage());
        }

    }
}
