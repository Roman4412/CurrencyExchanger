package com.projects.study.exception;

public class ExchangeRateAlreadyExistException extends RuntimeException {
    public ExchangeRateAlreadyExistException(String message) {
        super(message);
    }

}
