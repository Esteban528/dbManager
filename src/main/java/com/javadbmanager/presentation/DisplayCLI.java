package com.javadbmanager.presentation;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.javadbmanager.presentation.exceptions.EmptyValueException;

public class DisplayCLI implements Display {
  private ArrayList<Message> logs = new ArrayList<>();
  private Scanner scanner;
  private int CLI_SIZE = 100;
  private final char BORDER_CHAR = '*';
  private final char SPACE_CHAR = ' ';

  public DisplayCLI() {
    this.scanner = new Scanner(System.in);
    CLI_SIZE = TerminalUtils.getWidth();
    if (CLI_SIZE % 2 != 0) {
      CLI_SIZE--;
    }
  }

  public void sendErrorLog(String log) {
    sendLog(String.format("\u001B[31m%s\u001B[0m\n", log), 2);
  }

  @Override
  public void sendSuccessLog(String log) {
    sendLog(String.format("\u001B[31m%s\u001B[0m\n", log), 2);
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
    int option;
    try {
      option = this.scanner.nextInt();
    } catch (Exception e) {
      throw new EmptyValueException();
    }

    return option;
  }

  public double scanDouble() {
    double option;
    try {
      option = this.scanner.nextDouble();
    } catch (Exception e) {
      option = 0.0;
    }

    return option;
  }

  public String scanLine() {
    String option = this.scanner.nextLine();
    return option;
  }

  public void show() {
    cleanTerminal();

    printBorder();
    printEmptyLine();

    for (Message log : logs) {
      int alives = log.getAlives();
      log.setAlives(--alives);
      if (alives >= 0) {
        printCentered(log.getMessage());
      }
      if (alives <= 0) {
        logs.remove(log);
      }
    }

    printEmptyLine();
    printBorder();
  }

  public void clean() {

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
