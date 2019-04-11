package com.somelogs.distribution.soa.model;

import lombok.Data;

/**
 * SOA 接口返回
 *
 * @author LBG - 2018/1/3 0003
 */
@Data
public class Response<T> {

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 消息
     */
    private String message;

    /**
     * 响应数据
     */
    private T data;

    public Response() {
        this.code = ResponseStatus.SUCCESS.getCode();
        this.message = ResponseStatus.SUCCESS.getMessage();
    }
}
