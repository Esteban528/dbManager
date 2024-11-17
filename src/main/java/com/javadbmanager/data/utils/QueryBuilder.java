package com.javadbmanager.data.utils;

import java.util.Map;

import com.javadbmanager.data.exceptions.ColumnNotFoundException;

/**
 * The {@code QueryBuilder} interface provides methods for constructing SQL queries
 * for various database operations, such as insert, select, update, delete, and DDL (Data Definition Language) commands.
 */
public interface QueryBuilder {

    /**
     * Constructs an SQL INSERT statement.
     * @param tableName The name of the table to insert into.
     * @param items A map where keys are column names and values are the values to insert.
     * @param columnsTable A map containing the table's columns and their data types.
     * @return The formatted SQL INSERT statement.
     * @throws ColumnNotFoundException if a column specified in {@code items} is not found in {@code columnsTable}.
     */
    String makeInsert(String tableName, Map<String, String> items, Map<String, String> columnsTable)
            throws ColumnNotFoundException;

    /**
     * Constructs an SQL SELECT statement.
     * @param tableName The name of the table to select from.
     * @param wheres A map representing the WHERE clause, where keys are column names and values are conditions.  Can be null or empty for no WHERE clause.
     * @param columnsTable A map containing the table's columns and their data types.
     * @return The formatted SQL SELECT statement.
     */
    String makeSelect(String tableName, Map<String, String> wheres, Map<String, String> columnsTable);

    /**
     * Constructs an SQL UPDATE statement.
     * @param tableName The name of the table to update.
     * @param items A map where keys are column names and values are the new values.
     * @param wheres A map representing the WHERE clause, where keys are column names and values are conditions.
     * @param columnsTable A map containing the table's columns and their data types.
     * @return The formatted SQL UPDATE statement.
     * @throws ColumnNotFoundException if a column specified in {@code items} or {@code wheres} is not found in {@code columnsTable}.
     */
    String makeUpdate(String tableName, Map<String, String> items, Map<String, String> wheres,
                       Map<String, String> columnsTable) throws ColumnNotFoundException;

    /**
     * Constructs an SQL DELETE statement.
     * @param tableName The name of the table to delete from.
     * @param wheres A map representing the WHERE clause, where keys are column names and values are conditions.
     * @param columnsTable A map containing the table's columns and their data types.
     * @return The formatted SQL DELETE statement.
     * @throws ColumnNotFoundException if a column specified in {@code wheres} is not found in {@code columnsTable}.
     */
    String makeDelete(String tableName, Map<String, String> wheres, Map<String, String> columnsTable)
            throws ColumnNotFoundException;

    /**
     * Constructs an SQL CREATE TABLE statement.
     * @param tableName The name of the table to create.
     * @param values A map where keys are column names and values are their data types and constraints
     *              (e.g., "name VARCHAR(255) NOT NULL").
     * @return The formatted SQL CREATE TABLE statement.
     */
    String makeCreateTable(String tableName, Map<String, String> values);


    /**
     * Constructs an SQL statement to add a column to a table (ALTER TABLE ... ADD COLUMN).
     * @param tableName  The name of the table to modify.
     * @param columnName The name of the new column.
     * @param dataType   The data type of the new column (including size if applicable, e.g. "VARCHAR(255)").
     * @param constraints Optional constraints for the new column (e.g., "NOT NULL", "PRIMARY KEY").
     * @return The formatted SQL ALTER TABLE statement.
     */
    String makeCreateTableColumn(String tableName, String columnName, String dataType, String... constraints);



    /**
     * Constructs an SQL DROP TABLE statement.
     * @param tableName The name of the table to drop.
     * @return The formatted SQL DROP TABLE statement.
     */
    String makeDropTable(String tableName);

    /**
     * Constructs an SQL statement to drop a column from a table (ALTER TABLE ... DROP COLUMN).
     * @param tableName The name of the table to modify.
     * @param column The name of the column to drop.
     * @return The formatted SQL ALTER TABLE statement.
     */
    String makeDropColumn(String tableName, String column);

    /**
     * Constructs an SQL statement to alter a table's column constraints (ALTER TABLE ... MODIFY COLUMN).
     * @param tableName The name of the table to modify.
     * @param column The name of the column to alter.
     * @param constraints The new constraints for the column (e.g., "NOT NULL", "PRIMARY KEY").
     * @return The formatted SQL ALTER TABLE statement.
     */
    String makeAlterTable(String tableName, String column, String... constraints);

    /**
     * Constructs an SQL SELECT statement with a LIMIT clause.
     * @param tableName The name of the table to select from.
     * @param limit The maximum number of rows to retrieve.
     * @return The formatted SQL SELECT statement with LIMIT.
     */
    String makeSelectWithLimit(String tableName, int limit);


    /**
     * Constructs an SQL statement to rename a column (ALTER TABLE ... RENAME COLUMN).
     * @param tableName  The name of the table containing the column.
     * @param columnName The current name of the column.
     * @param newName    The new name for the column.
     * @return The formatted SQL ALTER TABLE statement.
     */
    String makeRenameColumn(String tableName, String columnName, String newName);
}
