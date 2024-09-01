package com.javadbmanager.business.delegate.menu;

import com.javadbmanager.business.logic.events.Command;

public class CommandHandlerImpl implements CommandHandler {
  private String title = "";
  private Command command;

  public void setTitle(String title) {
    this.title = title;
  }

  public void setCommand(Command command) {
    this.command = command;
  }

  public String getTitle() {
    return title;
  }

  public void execute() {
    this.command.execute();
  }

  public static CommandHandlerBuilder commandBuilder() {
    return new CommandHandlerBuilder();
  }

  public Command getCommand() {
    return command;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((title == null) ? 0 : title.hashCode());
    result = prime * result + ((command == null) ? 0 : command.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    CommandHandlerImpl other = (CommandHandlerImpl) obj;
    if (title == null) {
      if (other.title != null)
        return false;
    } else if (!title.equals(other.title))
      return false;
    if (command == null) {
      if (other.command != null)
        return false;
    } else if (!command.equals(other.command))
      return false;
    return true;
  }

}
