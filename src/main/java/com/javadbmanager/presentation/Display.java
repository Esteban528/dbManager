package com.javadbmanager.presentation;

import java.util.List;

import com.javadbmanager.presentation.exceptions.EmptyValueException;

/**
 * The {@code Display} interface provides methods for interacting with the user
 * interface,
 * including displaying logs, handling user input, and managing the display's
 * state.
 */
public interface Display {

    /**
     * Sends a general log message to the display.
     * 
     * @param log The log message to be displayed.
     */
    void sendLog(String log);

    /**
     * Sends an error log message to the display. This might involve visual cues
     * like using a different color or icon to indicate an error.
     * 
     * @param log The error log message to be displayed.
     */
    void sendErrorLog(String log);

    /**
     * Sends a success log message to the display. This could be visually distinct
     * from other log types, perhaps using a green color or a checkmark icon.
     * 
     * @param log The success log message to be displayed.
     */
    void sendSuccessLog(String log);

    /**
     * Adds a log message to the display's log buffer. This method might not
     * immediately
     * display the log but store it until {@link #show()} is called.
     * 
     * @param log The log message to be added to the buffer.
     */
    void addLog(String log);

    /**
     * Scans and returns an integer input from the user.
     * 
     * @return The integer entered by the user.
     * @throws EmptyValueException if the user provides an empty or invalid input.
     */
    int scan() throws EmptyValueException;

    /**
     * Scans and returns a double-precision floating-point input from the user.
     * 
     * @return The double value entered by the user.
     * @throws EmptyValueException if the user provides an empty or invalid input.
     */
    double scanDouble() throws EmptyValueException;

    /**
     * Scans and returns a line of text input from the user.
     * 
     * @return The line of text entered by the user.
     */
    String scanLine();

    /**
     * Displays the accumulated logs or refreshes the display to show the latest
     * content.
     */
    void show();

    /**
     * Retrieves a list of the current log messages.
     * 
     * @return A list containing the log messages.
     */
    List<String> getLogs();

    /**
     * Clears the display and removes any existing log messages or content.
     */
    void clean();

    /**
     * Closes the display and releases any associated resources.
     */
    void close();
}
