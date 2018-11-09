package com.somelogs.zookeeper;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

/**
 * 获取数据 权限控制
 *
 * @author LBG - 2018/11/9 0009
 */
public class AuthGetDataSample {

    private static final String PATH = "/zk-book-test";

    public static void main(String[] args) throws Exception {
        //noAuthTest();
        errorAuthTest();
    }

    private static void noAuthTest() throws Exception {
        ZooKeeper zooKeeper = new ZooKeeper("127.0.0.1:2181", 5000, null);
        zooKeeper.addAuthInfo("digest", "foo:true".getBytes());
        zooKeeper.create(PATH, "init".getBytes(), ZooDefs.Ids.CREATOR_ALL_ACL, CreateMode.EPHEMERAL);

        ZooKeeper zooKeeper2 = new ZooKeeper("127.0.0.1:2181", 5000, new CreateNodeSync());
        zooKeeper2.getData(PATH, false, null);
    }

    private static void errorAuthTest() throws Exception {
        ZooKeeper zooKeeper = new ZooKeeper("127.0.0.1:2181", 5000, null);
        zooKeeper.addAuthInfo("digest", "foo:true".getBytes());
        zooKeeper.create(PATH, "init".getBytes(), ZooDefs.Ids.CREATOR_ALL_ACL, CreateMode.EPHEMERAL);

        // correct auth
        ZooKeeper zooKeeper2 = new ZooKeeper("127.0.0.1:2181", 5000, new CreateNodeSync());
        zooKeeper2.addAuthInfo("digest", "foo:true".getBytes());
        System.out.println(new String(zooKeeper2.getData(PATH, false, null)));

        // wrong auth
        ZooKeeper zooKeeper3 = new ZooKeeper("127.0.0.1:2181", 5000, new CreateNodeSync());
        zooKeeper3.addAuthInfo("digest", "foo:false".getBytes());
        System.out.println(new String(zooKeeper3.getData(PATH, false, null)));
    }
}
