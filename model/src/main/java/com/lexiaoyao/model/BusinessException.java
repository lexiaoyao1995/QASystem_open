package com.lexiaoyao.model;

public class BusinessException extends RuntimeException {
    private ErrorType type;

    public BusinessException(ErrorType type) {
        super(type.getMessage());
        this.type = type;
    }

    public BusinessException(String message) {
        super(message);
    }

    public ErrorType getType() {
        return type;
    }

    public void setType(ErrorType type) {
        this.type = type;
    }
}
