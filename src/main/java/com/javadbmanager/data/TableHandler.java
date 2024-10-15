package com.javadbmanager.data;

import java.sql.SQLException;
import java.util.Map;

public interface TableHandler {
  void createTable(String tableName, Map<String, String> columns) throws SQLException;

  void removeTable(String tableName) throws SQLException;

  void createColumn(String tableName, String columnName, String dataType, String... constraints) throws SQLException;

  void removeColumn(String tableName, String columnName) throws SQLException;

  void editColumn(String tableName, String columnName, String... constraints) throws SQLException;

  void renameColumn(String tableName, String columnName, String newName) throws SQLException;

  Map<String, String> getTableProperties(String tableName) throws SQLException;

  Map<String, String> getTableColumns(String tableName) throws SQLException;
}
