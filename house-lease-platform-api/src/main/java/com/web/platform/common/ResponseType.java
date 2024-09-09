package com.web.platform.common;

/**
 * @author YaeMiko
 */

public enum ResponseType {
    FAILED("0", "操作失败"),
    SUCCESS("200", "success"),
    UNAUTHORIZED("403", "访问未授权"),
    SERVER_ERR("500", "未知的异常"),
    NOT_LOGIN("1000", "未登录"),
    ITEM_ADD_ERR("1001", "房源添加错误"),
    ITEM_DEL_ERR("1002", "房源删除错误"),
    REQUEST_ADD_ERR("1003", "求购添加错误"),
    REQUEST_DEL_ERR("1004", "求购删除错误"),
    LOGIN_FAILED("1005", "用户不存在或密码错误");

    private final String code;
    private final String msg;

    ResponseType(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }
}
