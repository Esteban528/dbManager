package com.javadbmanager.business.logic;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import com.javadbmanager.business.logic.exceptions.BusinessException;
import com.javadbmanager.data.AnyRepository;

/**
 * The {@code DataService} interface provides methods for performing CRUD (Create, Read, Update, Delete)
 * operations on data, abstracting the underlying data access mechanisms.  It follows the Service pattern
 * design, offering a higher-level interface for data manipulation.
 */
public interface DataService {

    /**
     * Sets the name of the table to be used for subsequent data operations.
     * @param tableName The name of the table. Should not be null or empty.
     */
    void setTableName(String tableName);

    /**
     * Retrieves the name of the currently active table for data operations.
     * @return The name of the table, or null if no table has been set.
     */
    String getTableName();

    /**
     * Inserts a new row into the table using the provided data.
     * @param items A map where keys represent column names and values represent the data to be inserted. Should not be null.
     * @return The number of rows affected by the insert operation (typically 1 if successful).
     * @throws BusinessException if an error occurs during insertion (e.g., duplicate key, invalid data, database connection issues).
     */
    int insert(Map<String, String> items) throws BusinessException;

    /**
     * Retrieves all rows from the table.
     * @return A list of maps, where each map represents a row and contains key-value pairs for column names and their corresponding values.
     * @throws BusinessException if an error occurs during data retrieval (e.g., database connection issues).
     */
    List<Map<String, String>> get() throws BusinessException;

    /**
     * Retrieves rows from the table based on the specified WHERE clause criteria.
     * @param where A map representing the WHERE clause, where keys are column names and values are the conditions for filtering.
     * @return A list of maps, where each map represents a row that satisfies the WHERE clause and contains key-value pairs for column names and their corresponding values.
     * @throws BusinessException if an error occurs during data retrieval (e.g., invalid WHERE clause, database connection issues).
     */
    List<Map<String, String>> get(Map<String, String> where) throws BusinessException;

    /**
     * Updates rows in the table based on the provided data and WHERE clause criteria.
     * @param items  A map where keys represent column names and values represent the new data for updating. Should not be null.
     * @param wheres A map representing the WHERE clause, where keys are column names and values are conditions for filtering which rows to update.
     * @return The number of rows affected by the update operation.
     * @throws BusinessException if an error occurs during the update operation (e.g., invalid WHERE clause, database connection issues).
     */
    int update(Map<String, String> items, Map<String, String> wheres) throws BusinessException;

    /**
     * Deletes rows from the table based on the specified WHERE clause criteria.
     * @param where A map representing the WHERE clause, where keys are column names and values are the conditions for filtering which rows to delete.
     * @return The number of rows affected by the delete operation.
     * @throws BusinessException if an error occurs during the delete operation (e.g., invalid WHERE clause, database connection issues).
     */
    int delete(Map<String, String> where) throws BusinessException;

    /**
     * Commits any pending changes to the database. This makes the changes persistent.
     * @throws BusinessException if an error occurs during the commit operation (e.g., database connection issues).
     */
    void commit() throws BusinessException;

    /**
     * Commits any pending changes to the database and then closes the connection.
     * @throws BusinessException if an error occurs during the commit or close operation (e.g., database connection issues).
     */
    void commitAndClose() throws BusinessException;

    /**
     * Retrieves an instance of the {@code AnyRepository} used by the {@code DataService}.
     * This can be useful for accessing lower-level data access operations if needed.
     * @return The {@code AnyRepository} instance.
     */
    AnyRepository getAnyRepository();

    /**
     * Executes a given function with the resulting {@link Connection}.
     *
     * <p>This method is a utility for executing operations that require a JDBC connection. 
     * It builds the connection configuration, opens the connection, passes it to the given function,
     * and ensures the connection is properly closed afterward.</p>
     *
     * @param fun a {@link Function} that receives an open {@link Connection} and returns a result; must not be {@code null}
     * @return the result of applying the given function to the open connection, or {@code null} if the builder is {@code null} or an exception occurs
     */
    Object execute(Function<Connection, Object> fun);

    /**
     * Initializes the {@code DataService}. This might involve establishing connections,
     * setting up resources, or performing other necessary initialization tasks.
     */
    void init();
}
