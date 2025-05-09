package com.javadbmanager.presentation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import com.javadbmanager.presentation.exceptions.EmptyValueException;

public class DisplayCLI implements Display {
  private Set<Message> logs = new LinkedHashSet<>();
  // private List<Message> logs = new ArrayList<>();
  // private Scanner scanner;
  private int CLI_SIZE = 100;
  private final char BORDER_CHAR = '*';
  private final char SPACE_CHAR = ' ';
  private BufferedReader reader;

  public DisplayCLI() {
    // this.scanner = new Scanner(System.in);

    this.reader = new BufferedReader(new InputStreamReader(System.in));
    CLI_SIZE = DisplayUtils.getWidth();
    if (CLI_SIZE % 2 != 0) {
      CLI_SIZE--;
    }
  }

  public void sendErrorLog(String log) {
    sendLog(String.format("\u001B[31m%s\u001B[0m\n", log), 2);
  }

  @Override
  public void sendSuccessLog(String log) {
    sendLog(String.format("\u001B[32m%s\u001B[0m\n", log), 2);
  }

  public void sendLog(String log) {
    sendLog(log, 1);
  }

  private void sendLog(String log, int alives) {
    addLog(log, alives);
    show();
  }

  @Override
  public void addLog(String log) {
    addLog(log, 1);
  }

  public void addLog(String log, int alive) {
    Message message = new Message(log, alive);
    logs.add(message);
  }

  public int scan() throws EmptyValueException {
    String line = scanLine();
    if (line.isBlank())
      throw new EmptyValueException();

    return Integer.parseInt(line);
  }

  public double scanDouble() throws EmptyValueException {
    String line = scanLine();
    if (line.isBlank())
      throw new EmptyValueException();

    return Double.parseDouble(line);
  }

  public String scanLine() {
    try {
      var value = reader.readLine();
      System.out.println(value);
      return value;
    } catch (IOException e) {
      System.err.println("Read line error: " + e.getMessage());
      return "";
    }
  }

  public void close() {
    try {
      reader.close();
    } catch (IOException e) {
      System.err.println("Close reader error: " + e.getMessage());
    }
  }

  public void show() {
    cleanTerminal();

    // printBorder();
    // printEmptyLine();

    this.logs.forEach(log -> {
      int alives = log.getAlives();

      if (alives >= 1) {
        // printCentered(log.getMessage());
        System.out.println(log.getMessage());
      }

      // if (alives <= 0) {
      // logs.remove(log);
      // }

      // log.setAlives(alives--);
    });
    // printEmptyLine();
    // printBorder();
  }

  public void clean() {
    logs.clear();
  }

  public void cleanTerminal() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }

  public List<String> getLogs() {
    return this.logs.stream()
        .map(x -> x.getMessage())
        .toList();
  }

  private void printBorder() {
    for (int i = 0; i < CLI_SIZE; i++) {
      System.out.print(BORDER_CHAR);
    }
    System.out.println();
  }

  private void printEmptyLine() {
    System.out.print(BORDER_CHAR);
    for (int i = 0; i < CLI_SIZE - 2; i++) {
      System.out.print(SPACE_CHAR);
    }
    System.out.println(BORDER_CHAR);
  }

  private void printCentered(String text) {
    int padding = (CLI_SIZE - text.length() - 2) / 2;
    System.out.print(BORDER_CHAR);
    for (int i = 0; i < padding; i++) {
      System.out.print(SPACE_CHAR);
    }
    System.out.print(text);
    for (int i = 0; i < CLI_SIZE - text.length() - padding - 2; i++) {
      System.out.print(SPACE_CHAR);
    }
    System.out.println(BORDER_CHAR);
  }

}
