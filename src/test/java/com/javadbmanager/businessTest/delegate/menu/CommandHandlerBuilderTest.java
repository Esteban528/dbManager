package com.javadbmanager.businessTest.delegate.menu;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.javadbmanager.business.delegate.menu.CommandHandlerBuilder;
import com.javadbmanager.business.delegate.menu.CommandHandlerImpl;
import com.javadbmanager.business.logic.events.Command;

public class CommandHandlerBuilderTest {

  @Test
  void testBuilder() {
    String title = "expectedTest Title";
    Command command = new Command() {
      public void execute() {
      }
    };

    var expect = new CommandHandlerImpl();
    expect.setTitle(title);
    expect.setCommand(command);

    CommandHandlerImpl actual = new CommandHandlerBuilder()
        .setTitle(title)
        .setCommand(command)
        .build();

    assertEquals(expect, actual);
  }
}
