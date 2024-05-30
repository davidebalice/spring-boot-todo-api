package com.todoapi.config;

public class CustomException extends RuntimeException {
    private int errorCode;
    private String message;

    public CustomException(int errorCode, String message) {
        this.message = message;
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
