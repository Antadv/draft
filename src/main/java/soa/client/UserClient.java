package soa.client;

import soa.annotation.SOARequestURL;
import soa.model.AddUserRequest;

/**
 * User Client 提供外部调用
 *
 * @author LBG - 2018/1/3 0003
 */
@SOARequestURL("/user")
public interface UserClient {

    @SOARequestURL("/addUser")
    Long addUser(AddUserRequest param);
}
