package com.projects.study.dto;

public class ExceptionResponse {
    String message;

    public ExceptionResponse(String message) {
        this.message = message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
