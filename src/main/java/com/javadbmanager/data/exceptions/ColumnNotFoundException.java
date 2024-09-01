package com.javadbmanager.data.exceptions;

public class ColumnNotFoundException extends RuntimeException {
  public ColumnNotFoundException(String columnName, String tableName) {
    super(String.format("The column %s does not exist in table %s", columnName, tableName));
  }
}
