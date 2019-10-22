package com.lexiaoyao.model;

public enum ErrorType {
    USER_ALREADY_EXIST("用户已存在");
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
