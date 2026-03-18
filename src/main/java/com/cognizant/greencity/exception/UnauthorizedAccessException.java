package com.cognizant.greencity.exception;

/**
 * Exception thrown when user tries unauthorized operation (e.g., citizen updating status)
 * HTTP Status: 403 Forbidden
 */
public class UnauthorizedAccessException extends RuntimeException {
    public UnauthorizedAccessException(String message) {
        super(message);
    }

    public UnauthorizedAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}
