package com.somelogs.javase.proxy;

import lombok.Data;

import java.util.List;

/**
 * 描述
 * Created by liubingguang on 2017/5/26.
 */
@Data
public class User {

    private Integer id;
    private String name;
    private List<String> hobbyList;
    private List<User> userList;
}
