package com.javadbmanager.business.delegate.menu.exceptions;

/**
 * A custom runtime exception that represents errors related to menu operations, such as
 * loading a menu, executing a menu command, or managing menu state.
 */
public class MenuException extends RuntimeException {

    /**
     * Constructs a new {@code MenuException} with a descriptive error message.
     * @param message A detailed message explaining the cause of the exception.
     */
    public MenuException(String message) {
        super(message);
    }
}
