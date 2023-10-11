package com.forDece.framework.exception;

public class BeansException extends Exception {
    public enum ErrorType {}

    private final ErrorType errorType;

    public BeansException(ErrorType errorType, String message) {
        super(message);
        this.errorType = errorType;
    }
    public ErrorType getErrorType() {
        return errorType;
    }
}
