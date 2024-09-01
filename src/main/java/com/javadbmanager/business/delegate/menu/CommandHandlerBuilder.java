package com.javadbmanager.business.delegate.menu;

import com.javadbmanager.business.logic.events.Command;

public class CommandHandlerBuilder {
  private CommandHandlerImpl commandHandlerImpl;

  public CommandHandlerBuilder() {
    commandHandlerImpl = new CommandHandlerImpl();
  }

  public CommandHandlerBuilder setTitle(String title) {
    commandHandlerImpl.setTitle(title);
    return this;
  }

  public CommandHandlerBuilder setCommand(Command command) {
    commandHandlerImpl.setCommand(command);
    return this;
  }

  public CommandHandlerImpl build() {
    return commandHandlerImpl;
  }
}
