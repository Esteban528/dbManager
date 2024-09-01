package com.javadbmanager.data.utils;

import java.util.Map;

import com.javadbmanager.data.exceptions.ColumnNotFoundException;

public interface QueryBuilder {

  String makeInsert(String tableName, Map<String, String> items, Map<String, String> columnsTable)
      throws ColumnNotFoundException;

  String makeSelect(String tableName, Map<String, String> wheres, Map<String, String> columnsTable);

  String makeUpdate(String tableName, Map<String, String> items, Map<String, String> wheres,
      Map<String, String> columnsTable) throws ColumnNotFoundException;

  String makeDelete(String tableName, Map<String, String> wheres, Map<String, String> columnsTable)
      throws ColumnNotFoundException;

  String makeCreateTable(String tableName, Map<String, String> values);

  String makeCreateTableColumn(String tableName, String columnName, String dataType, String... constraints);

  String makeDropTable(String tableName);

  String makeDropColumn(String tableName, String column);

  String makeAlterTable(String tableName, String column, String... constraints);

  String makeSelectWithLimit(String tableName, int limit);

  String makeRenameColumn(String tableName, String columnName, String newName);
}
