package com.cognizant.greencity.exception;

/**
 * Exception thrown for internal server errors
 * HTTP Status: 500 Internal Server Error
 */
public class InternalServiceError extends RuntimeException {
    public InternalServiceError(String message) {
        super(message);
    }

    public InternalServiceError(String message, Throwable cause) {
        super(message, cause);
    }
}
