package com.somelogs.javase.proxy;

/**
 * 描述
 * Created by liubingguang on 2017/5/26.
 */
public interface UserDao {

    void insertUser(User user);

    User getUserByName(String name);
}
