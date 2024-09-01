package com.javadbmanager.data.utils;

import java.util.Iterator;
import java.util.Map;
import com.javadbmanager.data.exceptions.ColumnNotFoundException;

public class QueryBuilderImpl implements QueryBuilder {

  public String makeInsert(String tableName, Map<String, String> items, Map<String, String> columnsTable)
      throws ColumnNotFoundException {
    String query;
    StringBuilder columns = new StringBuilder(), values = new StringBuilder();

    Iterator<String> iterator = items.keySet().iterator();
    int remainingElements = items.size();
    while (iterator.hasNext()) {
      remainingElements--;
      String key = iterator.next();

      if (columnsTable.get(key) == null) {
        throw new ColumnNotFoundException(key, tableName);
      }

      String comma = ((remainingElements > 0) ? ", " : "");
      columns.append(key + comma);
      values.append(String.format("'%s'" + comma, items.get(key)));
    }

    query = String.format("INSERT INTO %s (%s) VALUES(%s)", tableName, columns.toString(), values.toString());
    return query;
  }

  public String makeSelect(String tableName, Map<String, String> wheres, Map<String, String> columnsTable) {
    String query;
    StringBuilder wherePart = new StringBuilder();

    Iterator<String> iterator = wheres.keySet().iterator();
    int remainingElements = wheres.size();
    while (iterator.hasNext()) {
      remainingElements--;
      String key = iterator.next();

      if (columnsTable.get(key) == null) {
        throw new ColumnNotFoundException(key, tableName);
      }

      String sqlSeparator = ((remainingElements > 0) ? " AND " : "");
      wherePart.append(String.format(" %s = '%s' %s", key, wheres.get(key), sqlSeparator));
    }

    query = String.format("SELECT * FROM %s %s %s", tableName, (!wheres.isEmpty() ? "WHERE" : ""),
        wherePart.toString());
    return query;
  }

  public String makeUpdate(String tableName, Map<String, String> items, Map<String, String> wheres,
      Map<String, String> columnsTable) {

    String query;
    StringBuilder updateValues = new StringBuilder(), whereValues = new StringBuilder();

    Iterator<String> iterator = items.keySet().iterator();
    int remainingElements = items.size();

    if (!wheres.isEmpty()) {
      whereValues.append(" WHERE ");
    }

    while (iterator.hasNext()) {
      remainingElements--;
      String key = iterator.next();

      if (columnsTable.get(key) == null) {
        throw new ColumnNotFoundException(key, tableName);
      }

      String sqlSeparator = ((remainingElements > 0) ? " , " : "");
      updateValues.append(String.format("%s = '%s' %s", key, items.get(key), sqlSeparator));
    }

    iterator = wheres.keySet().iterator();
    remainingElements = wheres.size();

    while (iterator.hasNext()) {
      remainingElements--;
      String key = iterator.next();

      if (columnsTable.get(key) == null) {
        throw new ColumnNotFoundException(key, tableName);
      }

      String sqlSeparator = ((remainingElements > 0) ? " AND " : "");
      whereValues.append(String.format("%s = '%s' %s", key, wheres.get(key), sqlSeparator));
    }

    query = String.format("UPDATE %s SET %s %s", tableName, updateValues.toString(), whereValues.toString());
    return query;
  }

  public String makeDelete(String tableName, Map<String, String> wheres, Map<String, String> columnsTable) {
    String query;
    StringBuilder wherePart = new StringBuilder();

    Iterator<String> iterator = wheres.keySet().iterator();
    int remainingElements = wheres.size();
    while (iterator.hasNext()) {
      remainingElements--;
      String key = iterator.next();

      if (columnsTable.get(key) == null) {
        throw new ColumnNotFoundException(key, tableName);
      }

      String sqlSeparator = ((remainingElements > 0) ? "AND " : "");
      wherePart.append(String.format("%s = '%s' %s", key, wheres.get(key), sqlSeparator));
    }

    query = String.format("DELETE %s WHERE %s", tableName, wherePart.toString());
    return query;
  }

  public String makeCreateTable(String tableName, Map<String, String> values) {
    String query, columnName, args, sqlSeparator;
    StringBuilder valueString = new StringBuilder();

    Iterator<String> iterator = values.keySet().iterator();
    int remainingElements = values.size();
    while (iterator.hasNext()) {
      remainingElements--;
      columnName = iterator.next();
      args = values.get(columnName);

      sqlSeparator = ((remainingElements > 0) ? ", " : "");
      valueString.append(columnName);
      valueString.append(" ");
      valueString.append(args);
      valueString.append(sqlSeparator);
    }
    query = String.format("CREATE TABLE IF NOT EXISTS %s (%s)", tableName, valueString.toString());

    return query;
  }

  public String makeCreateTableColumn(String tableName, String columnName, String dataType,
      String... constraints) {

    String query;
    StringBuilder constraint = new StringBuilder();

    for (String word : constraints) {
      constraint.append(word);
      constraint.append(" ");
    }

    query = String.format("ALTER TABLE %s ADD %s %s %s", tableName, columnName, dataType, constraint);

    return query;
  }

  public String makeDropTable(String tableName) {
    return String.format("DROP TABLE %s", tableName);
  }

  public String makeDropColumn(String tableName, String column) {
    return String.format("ALTER TABLE %s DROP COLUMN %s", tableName, column);
  }

  public String makeAlterTable(String tableName, String column, String... constraints) {
    StringBuilder queryConstraints = new StringBuilder();

    for (String string : constraints) {
      queryConstraints.append(string);
      queryConstraints.append(" ");
    }

    return String.format("ALTER TABLE %s MODIFY COLUMN %s %s", tableName, column, queryConstraints.toString());
  }

  public String makeSelectWithLimit(String tableName, int limit) {
    return String.format("SELECT * FROM %s LIMIT %d", tableName, limit);
  }

  public String makeRenameColumn(String tableName, String columnName, String newName) {
    return String.format("ALTER TABLE %s RENAME COLUMN %s to %s;", tableName, columnName, newName);
  }
}
