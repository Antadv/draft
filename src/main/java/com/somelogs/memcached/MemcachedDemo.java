package com.somelogs.memcached;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.transcoders.StringTranscoder;
import net.rubyeye.xmemcached.utils.AddrUtil;

/**
 * 描述
 *
 * @author LBG - 2017/11/3 0003
 */
public class MemcachedDemo {

    public static void main(String[] args) throws Exception {
        MemcachedClientBuilder builder
                = new XMemcachedClientBuilder(AddrUtil.getAddresses("106.15.182.185:11211"));
        MemcachedClient client = builder.build();
        client.flushAll();
        if (!client.set("hello", 0, "world")) {
            System.err.println("set error");
        }
        if (!client.add("hello", 0, "dennis")) {
            System.err.println("Add error,key is existed!");
        }
        if (!client.replace("hello", 0, "dennis")) {
            System.err.println("replace error");
        }
        client.append("hello", " good");
        client.prepend("hello", "hello ");
        String name = client.get("hello", new StringTranscoder());
        System.out.println(name);
        client.deleteWithNoReply("hello");
        client.shutdown();
    }
}
