package com.cognizant.greencity.exception;

public class InternalServiceError extends RuntimeException {
    public InternalServiceError(String message) {
        super(message);
    }

    public InternalServiceError(String message, Throwable cause) {
        super(message, cause);
    }
}
