package com.javadbmanager.data;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.javadbmanager.data.exceptions.ColumnNotFoundException;
import com.javadbmanager.data.utils.DataUtils;

/**
 * The {@code AnyRepository} interface defines a generic data access repository for performing CRUD
 * (Create, Read, Update, Delete) operations on a database table.  It provides an abstraction
 * over the underlying data access mechanism, allowing for more flexible and reusable data
 * manipulation logic.
 */
public interface AnyRepository {

    /**
     * Gets the name of the table associated with this repository.
     * @return The name of the table.
     */
    String getTableName();

    /**
     * Sets the name of the table for this repository and initializes it using the provided {@code DataUtils}.
     * This might involve retrieving table metadata or performing other setup operations.
     * @param tableName The name of the table. Should not be null or empty.
     * @param dataUtils The {@code DataUtils} instance to use for utility functions. Should not be null.
     */
    void setTableName(String tableName, DataUtils dataUtils);

    /**
     * Inserts a new row into the table.
     * @param items A map representing the data to be inserted, where keys are column names and values are the corresponding values.
     * @return The number of rows affected by the insert operation (typically 1).
     * @throws SQLException If a database access error occurs during insertion.
     * @throws ColumnNotFoundException If a column specified in {@code items} does not exist in the table.
     */
    int insert(Map<String, String> items) throws SQLException, ColumnNotFoundException;

    /**
     * Retrieves all rows from the table.
     * @return A list of maps, where each map represents a row and contains key-value pairs for column names and their values.
     * @throws SQLException If a database access error occurs during retrieval.
     */
    List<Map<String, String>> get() throws SQLException;

    /**
     * Retrieves rows from the table based on the specified criteria.
     * @param wheres A map representing the WHERE clause, where keys are column names and values are the conditions for filtering.
     * @return A list of maps, where each map represents a row matching the criteria and contains key-value pairs for column names and their values.
     * @throws SQLException If a database access error occurs during retrieval.
     * @throws ColumnNotFoundException If a column specified in {@code wheres} does not exist in the table.
     */
    List<Map<String, String>> get(Map<String, String> wheres) throws SQLException, ColumnNotFoundException;

    /**
     * Updates rows in the table based on the specified criteria.
     * @param items A map representing the data to update, where keys are column names and values are the new values.
     * @param wheres A map representing the WHERE clause, where keys are column names and values are the conditions for filtering.
     * @return The number of rows affected by the update operation.
     * @throws SQLException If a database access error occurs during the update.
     * @throws ColumnNotFoundException If a column specified in {@code items} or {@code wheres} does not exist in the table.
     */
    int update(Map<String, String> items, Map<String, String> wheres) throws SQLException, ColumnNotFoundException;

    /**
     * Deletes rows from the table based on the specified criteria.
     * @param wheres A map representing the WHERE clause, where keys are column names and values are the conditions for filtering.
     * @return The number of rows affected by the delete operation.
     * @throws SQLException If a database access error occurs during deletion.
     * @throws ColumnNotFoundException If a column specified in {@code wheres} does not exist in the table.
     */
    int delete(Map<String, String> wheres) throws SQLException, ColumnNotFoundException;

    /**
     * Commits any pending changes to the database.  This persists any insert, update, or
     * delete operations that have been performed since the last commit.
     * @throws SQLException If a database access error occurs during the commit operation.
     */
    void commitChange() throws SQLException;
}
