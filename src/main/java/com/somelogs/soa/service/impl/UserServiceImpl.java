package com.somelogs.soa.service.impl;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;
import com.somelogs.soa.service.UserService;

/**
 * user service implementation
 *
 * @author LBG - 2018/1/3 0003
 */
@Service
public class UserServiceImpl implements UserService {

    @Override
    public Long addUser(String username, String mobile) {
        System.out.println(username + "-" + mobile);
        return RandomUtils.nextLong(1L, 100L);
    }
}
