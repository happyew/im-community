package com.example.imcommunity.exception;

public enum CustomErrorCode implements ICustomErrorCode {
    QUESTION_NOT_FOUND(404, "找不到资源..."),
    NOT_LOGIN(404, "未登录..."),
    LOGIN_FAILED(404, "登陆失败..."),
    USERNAME_EXISTED(403,"该用户名已存在");

    CustomErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    private final Integer code;
    private final String message;

    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
