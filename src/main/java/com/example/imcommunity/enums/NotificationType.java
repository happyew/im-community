package com.example.imcommunity.enums;

public enum NotificationType {
    QUESTION(0, "提问"),
    COMMENT(1, "评论");

    private final int code;
    private final String name;

    NotificationType(int code, String name) {
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
