package com.lexiaoyao.model;

public enum ErrorType {
    USER_ALREADY_EXIST("用户已存在"),
    USERNAME_NOT_EXIST("用户名不存在"),
    JWT_EXPIRED("TOKEN过期"),
    TOPIC_EXIST("主题名已存在");


    private String message;

    ErrorType(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
