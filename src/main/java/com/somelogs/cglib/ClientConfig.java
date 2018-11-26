package com.somelogs.cglib;

import lombok.Data;

/**
 * com.somelogs.client config
 * Created by liubingguang on 2017/8/11.
 */
@Data
public class ClientConfig {

    private String name;
    private String code;

    public ClientConfig(String name, String code) {
        this.name = name;
        this.code = code;
    }
}

