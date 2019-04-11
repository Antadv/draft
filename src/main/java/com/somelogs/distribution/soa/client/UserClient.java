package com.somelogs.distribution.soa.client;

import com.somelogs.distribution.soa.annotation.SOARequestURL;
import com.somelogs.distribution.soa.model.AddUserRequest;
import com.somelogs.distribution.soa.model.Response;

/**
 * User Client 提供外部调用
 *
 * @author LBG - 2018/1/3 0003
 */
@SOARequestURL(url = "/user")
public interface UserClient {

    @SOARequestURL(url = "/addUser")
    Response<Long> addUser(AddUserRequest param);
}
