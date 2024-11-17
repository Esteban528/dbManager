package com.javadbmanager.data;

import java.sql.SQLException;
import java.util.Map;

/**
 * This interface defines the methods for managing tables in a database.
 * 
 * @author Your Name
 */
public interface TableHandler {
  /**
   * Creates a new table in the database.
   * 
   * @param tableName The name of the table to create.
   * @param columns The columns of the table.
   * @throws SQLException If an error occurs while creating the table.
   */
  void createTable(String tableName, Map<String, String> columns) throws SQLException;

  /**
   * Removes a table from the database.
   * 
   * @param tableName The name of the table to remove.
   * @throws SQLException If an error occurs while removing the table.
   */
  void removeTable(String tableName) throws SQLException;

  /**
   * Creates a new column in the specified table.
   * 
   * @param tableName The name of the table.
   * @param columnName The name of the column.
   * @param dataType The data type of the column.
   * @param constraints Additional constraints for the column.
   * @throws SQLException If an error occurs while creating the column.
   */
  void createColumn(String tableName, String columnName, String dataType, String... constraints) throws SQLException;

  /**
   * Removes a column from the specified table.
   * 
   * @param tableName The name of the table.
   * @param columnName The name of the column to remove.
   * @throws SQLException If an error occurs while removing the column.
   */
  void removeColumn(String tableName, String columnName) throws SQLException;

  /**
   * Edits the constraints of a column in the specified table.
   * 
   * @param tableName The name of the table.
   * @param columnName The name of the column.
   * @param constraints The new constraints for the column.
   * @throws SQLException If an error occurs while editing the column constraints.
   */
  void editColumn(String tableName, String columnName, String... constraints) throws SQLException;

  /**
   * Renames a column in the specified table.
   * 
   * @param tableName The name of the table.
   * @param columnName The name of the column to rename.
   * @param newName The new name for the column.
   * @throws SQLException If an error occurs while renaming the column.
   */
  void renameColumn(String tableName, String columnName, String newName) throws SQLException;

  /**
   * Retrieves the properties of a table.
   * 
   * @param tableName The name of the table.
   * @return The properties of the table.
   * @throws SQLException If an error occurs while retrieving the table properties.
   */
  Map<String, String> getTableProperties(String tableName) throws SQLException;

  /**
   * Retrieves the columns of a table.
   * 
   * @param tableName The name of the table.
   * @return The columns of the table.
   * @throws SQLException If an error occurs while retrieving the table columns.
   */
  Map<String, String> getTableColumns(String tableName) throws SQLException;
}
