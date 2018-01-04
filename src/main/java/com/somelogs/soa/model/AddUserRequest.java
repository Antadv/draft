package com.somelogs.soa.model;

import lombok.Data;

import java.util.List;

/**
 * 添加用户参数
 *
 * @author LBG - 2018/1/3 0003
 */
@Data
public class AddUserRequest {

    private String username;
    private String mobile;
    private List<Long> list;
}
