package com.javadbmanager.business.logic.events;

/**
 * Command
 */
@FunctionalInterface
public interface Command {
  void execute();
}
