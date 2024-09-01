package com.javadbmanager.business.delegate.menu;

import com.javadbmanager.business.logic.events.Command;

public interface CommandHandler {
  void setTitle(String title);

  void setCommand(Command command);

  String getTitle();

  void execute();
}
