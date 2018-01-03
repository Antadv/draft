package soa.model;

import lombok.Data;

/**
 * 添加用户参数
 *
 * @author LBG - 2018/1/3 0003
 */
@Data
public class AddUserRequest {

    private String username;
    private String mobile;
}
