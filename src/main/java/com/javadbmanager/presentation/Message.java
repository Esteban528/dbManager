package com.javadbmanager.presentation;

public class Message {
  private String message;
  private int alives;

  public Message(String message, int alives) {
    this.message = message;
    this.alives = alives;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public int getAlives() {
    return alives;
  }

  public void setAlives(int alives) {
    this.alives = alives;
  }

}
