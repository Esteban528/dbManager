package com.javadbmanager.presentationTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.javadbmanager.presentation.Display;
import com.javadbmanager.presentation.DisplayCLI;
import com.javadbmanager.presentation.exceptions.EmptyValueException;

public class DisplayCLITest {
  private Display display = new DisplayCLI();

  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private final PrintStream originalOut = System.out;
  private final InputStream originalIn = System.in;

  @BeforeEach
  public void setUpStreams() {
    System.setOut(new PrintStream(outContent));
  }

  @AfterEach
  public void restoreStreams() {
    System.setOut(originalOut);
    System.setIn(originalIn);
  }

  @Test
  public void testSendLog() {
    String testMessage = "Test Message";
    display.addLog(testMessage);

    assertEquals(testMessage, display.getLogs().get(0));
  }

  @Test
  public void testSendErrorLog() {
    String testMessage = "Test Message";
    String expected = String.format("\u001B[31m%s\u001B[0m\n", testMessage);

    display.sendErrorLog(testMessage);

    assertEquals(expected, display.getLogs().get(0));
  }

  @Test
  public void testScan() throws EmptyValueException {
    String simulatedInput = "5\n";
    System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

    Display displayCLI = new DisplayCLI();
    int result = displayCLI.scan();

    assertEquals(5, result);
  }

  @Test
  public void testScanWithError() {
    String simulatedInput = "Error here! An string is not a integer\n";
    System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

    Display displayCLI = new DisplayCLI();

    assertThrows(EmptyValueException.class, () -> {
      displayCLI.scan();
    });
  }

  @Test
  public void testScanLine() throws EmptyValueException {
    String simulatedInput = "Hello, World!\n";
    System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

    Display displayCLI = new DisplayCLI();
    String result = displayCLI.scanLine();

    assertEquals("Hello, World!", result);
  }
}
