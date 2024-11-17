package com.javadbmanager.business.delegate.menu;

import com.javadbmanager.business.logic.events.Command;

/**
 * The {@code CommandHandler} interface represents an object that can execute a specific command
 * associated with a menu option.  It provides methods for setting the title of the command,
 * associating a {@code Command} object, retrieving the title, and executing the command.
 */
public interface CommandHandler {

    /**
     * Sets the title or label of the command. This is typically displayed in the menu.
     * @param title The title of the command.
     */
    void setTitle(String title);

    /**
     * Sets the {@code Command} object to be executed.
     * @param command The command to be executed. Command it's a functinal interface
     */
    void setCommand(Command command);

    /**
     * Retrieves the title of the command.
     * @return The title of the command.
     */
    String getTitle();

    /**
     * Executes the associated command.  The implementation of this method should perform
     * the specific action associated with the command.
     */
    void execute();
}
