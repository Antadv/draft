package com.somelogs.soa.model;

/**
 * 响应状态枚举
 *
 * @author LBG - 2018/1/3 0003
 */
public enum ResponseStatus {

    SUCCESS(0, "成功"),
    INTERNAL_SERVER_ERROR(500, "系统内部错误");

    private Integer code;
    private String message;

    ResponseStatus(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
