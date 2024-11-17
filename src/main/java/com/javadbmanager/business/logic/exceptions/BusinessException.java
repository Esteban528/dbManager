package com.javadbmanager.business.logic.exceptions;

/**
 * A custom exception class representing errors that occur within the business logic layer.
 * This exception can wrap underlying exceptions (like data access exceptions) or be used
 * to signal business-specific error conditions.
 */
public class BusinessException extends Exception {

    /**
     * Constructs a new {@code BusinessException} with a detailed message and a root cause.
     * @param message A descriptive message explaining the business error.
     * @param cause   The underlying cause of the exception (e.g., a {@code SQLException}).
     *                This can be {@code null} if there is no root cause.
     */
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new {@code BusinessException} with a detailed message.
     * @param message A descriptive message explaining the business error.
     */
    public BusinessException(String message) {
        super(message);
    }
}
