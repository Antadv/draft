package com.somelogs.distribution.soa.service;

/**
 * User serivce
 *
 * @author LBG - 2018/1/3 0003
 */
public interface UserService {

    /**
     * 添加用户
     *
     * @param username 用户名
     * @param mobile 手机号
     * @return 用户id
     */
    Long addUser(String username, String mobile);
}
