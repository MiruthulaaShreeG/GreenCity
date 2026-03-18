package com.cognizant.greencity.exception;

/**
 * Exception thrown when request parameters are invalid or missing required fields
 * HTTP Status: 400 Bad Request
 */
public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
