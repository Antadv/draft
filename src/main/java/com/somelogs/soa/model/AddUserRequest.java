package com.somelogs.soa.model;

import java.util.List;

/**
 * 添加用户参数
 *
 * @author LBG - 2018/1/3 0003
 */
public class AddUserRequest {

    private String username;
    private String mobile;
    private List<Long> list;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public List<Long> getList() {
        return list;
    }

    public void setList(List<Long> list) {
        this.list = list;
    }
}
