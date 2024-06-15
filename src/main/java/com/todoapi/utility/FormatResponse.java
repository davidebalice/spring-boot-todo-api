package com.todoapi.utility;

public class FormatResponse {
    private String message;

    public FormatResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}