package com.javadbmanager.business.delegate.menu;

import com.javadbmanager.business.logic.events.Command;

/**
 * A builder class for creating {@code CommandHandlerImpl} objects. This class uses the builder pattern
 * to provide a flexible and readable way to construct command handlers.
 */
public class CommandHandlerBuilder {
    private CommandHandlerImpl commandHandlerImpl;

    /**
     * Creates a new {@code CommandHandlerBuilder}.
     */
    public CommandHandlerBuilder() {
        commandHandlerImpl = new CommandHandlerImpl();
    }

    /**
     * Sets the title of the command handler.
     * @param title The title to set.
     * @return This builder instance for method chaining.
     */
    public CommandHandlerBuilder setTitle(String title) {
        commandHandlerImpl.setTitle(title);
        return this;
    }

    /**
     * Sets the command to be executed by the command handler.
     * @param command The command to set.
     * @return This builder instance for method chaining.
     */
    public CommandHandlerBuilder setCommand(Command command) {
        commandHandlerImpl.setCommand(command);
        return this;
    }

    /**
     * Builds and returns a new {@code CommandHandlerImpl} instance with the configured properties.
     * @return The newly created {@code CommandHandlerImpl} instance.
     */
    public CommandHandlerImpl build() {
        return commandHandlerImpl;
    }
}
