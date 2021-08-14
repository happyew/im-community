package com.example.imcommunity.exception;

public class CustomException extends RuntimeException {
    private final Integer code;
    private final String message;

    public CustomException(ICustomErrorCode customErrorCode) {
        this.code = customErrorCode.getCode();
        this.message = customErrorCode.getMessage();
    }

    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
