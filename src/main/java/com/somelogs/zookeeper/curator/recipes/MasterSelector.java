package com.somelogs.zookeeper.curator.recipes;

import com.somelogs.zookeeper.curator.api.ClientCommonUtil;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;

/**
 * master 选举
 *
 * @author LBG - 2018/11/11 0011
 */
public class MasterSelector {

    public static void main(String[] args) throws InterruptedException {
        String masterPath = "/curator_master_path";
        CuratorFramework client = ClientCommonUtil.getClient();
        client.start();

        LeaderSelector selector = new LeaderSelector(client, masterPath, new LeaderSelectorListenerAdapter() {
            @Override
            public void takeLeadership(CuratorFramework client) throws Exception {
                System.out.println("成为 master 角色");
                Thread.sleep(3000);
                System.out.println("完成 Master 操作，释放 Master 权利");
            }
        });
        selector.autoRequeue();
        selector.start();
        Thread.sleep(Integer.MAX_VALUE);
    }
}
