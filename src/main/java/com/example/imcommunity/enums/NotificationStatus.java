package com.example.imcommunity.enums;

public enum NotificationStatus {
    NOT_READ(0, "未读"),
    READ(1, "已读");

    private final int code;
    private final String name;

    NotificationStatus(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }
}
