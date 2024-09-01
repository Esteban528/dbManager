package com.javadbmanager.presentation.exceptions;

public class EmptyValueException extends Exception {
  public EmptyValueException() {
    super("The value entered is empty.");
  }

  public EmptyValueException(String message) {
    super(message);
  }
}
