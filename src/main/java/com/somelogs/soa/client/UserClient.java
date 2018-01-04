package com.somelogs.soa.client;

import com.somelogs.soa.annotation.SOARequestURL;
import com.somelogs.soa.model.AddUserRequest;

/**
 * User Client 提供外部调用
 *
 * @author LBG - 2018/1/3 0003
 */
@SOARequestURL(url = "/user")
public interface UserClient {

    @SOARequestURL(url = "/addUser")
    Long addUser(AddUserRequest param);
}
