package soa.model;

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
}
