package com.somelogs.distribution.zookeeper;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

/**
 * 删除节点 权限控制
 *
 * @author LBG - 2018/11/9 0009
 */
public class AuthDeleteNodeSample {

    private static final String PATH = "/zk-book-test";
    private static final String PATH2 = "/zk-book-test/child";

    public static void main(String[] args) throws Exception {
        ZooKeeper zooKeeper = new ZooKeeper("127.0.0.1:2181", 5000, null);
        zooKeeper.addAuthInfo("digest", "foo:true".getBytes());
        zooKeeper.create(PATH, "init".getBytes(), ZooDefs.Ids.CREATOR_ALL_ACL, CreateMode.PERSISTENT);
        zooKeeper.create(PATH2, "init".getBytes(), ZooDefs.Ids.CREATOR_ALL_ACL, CreateMode.EPHEMERAL);

        // 没权限删除
        ZooKeeper zooKeeper2 = new ZooKeeper("127.0.0.1:2181", 5000, null);
        try {
            zooKeeper2.delete(PATH2, -1);
        } catch(Exception e) {
            System.out.println("删除节点失败" + e.getMessage());
        }

        // 有权限成功删除
        ZooKeeper zooKeeper3 = new ZooKeeper("127.0.0.1:2181", 5000, null);
        zooKeeper3.addAuthInfo("digest", "foo:true".getBytes());
        zooKeeper3.delete(PATH2, -1);
        System.out.println("成功删除节点 " + PATH2);

        /*
         * 当客户端对一个数据节点添加了权限信息后，杜宇删除操作而言，其作用范围是其子节点。
         * 也就是说，当我们对一个数据节点添加权限信息后，依然可以自由地删除这个节点，
         * 但对于这个节点的子节点，就必须使用相应的权限信息才能够删除它
         */
        ZooKeeper zooKeeper4 = new ZooKeeper("127.0.0.1:2181", 5000, null);
        zooKeeper4.delete(PATH, -1);
        System.out.println("成功删除节点 " + PATH);
    }
}
