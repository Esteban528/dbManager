package com.javadbmanager.businessTest.delegate.menu;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.javadbmanager.business.delegate.menu.CommandHandlerImpl;
import com.javadbmanager.business.logic.events.Command;

public class CommandHandlerImplTest {
  Command command;

  CommandHandlerImpl commandHandlerImpl;

  @BeforeEach
  void setUp() {
    command = mock(Command.class);
    commandHandlerImpl = new CommandHandlerImpl();
  }

  @Test
  void setTitleAndGetTitleTest() {
    String expect = "Command test";
    commandHandlerImpl.setTitle(expect);

    String actual = commandHandlerImpl.getTitle();

    assertEquals(expect, actual);
  }

  @Test
  void setCommandAndGetCommandTest() {
    Command expect = command;
    commandHandlerImpl.setCommand(expect);

    Command actual = commandHandlerImpl.getCommand();

    assertEquals(expect, actual);
  }

  @Test
  void executeTest() {
    commandHandlerImpl.setCommand(command);
    commandHandlerImpl.execute();

    verify(command).execute();
  }
}
