package com.javadbmanager.presentation;

import java.util.List;

import com.javadbmanager.presentation.exceptions.EmptyValueException;

/**
 * Display
 */
public interface Display {
  void sendLog(String log);

  void sendErrorLog(String log);

  void sendSuccessLog(String log);

  void addLog(String log);

  int scan() throws EmptyValueException;

  double scanDouble() throws EmptyValueException;

  String scanLine() throws EmptyValueException;

  void show();

  List<String> getLogs();

  void clean();
}
