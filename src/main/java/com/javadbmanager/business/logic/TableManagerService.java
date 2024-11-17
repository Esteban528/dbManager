package com.javadbmanager.business.logic;

import java.util.Map;

import com.javadbmanager.business.logic.exceptions.BusinessException;

/**
 * The {@code TableManagerService} interface provides methods for managing database tables,
 * abstracting the underlying operations performed by the data layer. This service follows
 * the Service pattern design, offering a higher-level interface for table manipulation.
 */
public interface TableManagerService {

    /**
     * Sets the name of the table to be managed. Subsequent operations will be performed
     * on this table.
     * @param tableName The name of the table.  Should not be null or empty.
     */
    void setTableName(String tableName);

    /**
     * Initializes the {@code TableManagerService}.  This might involve loading table metadata,
     * establishing connections, or performing other setup tasks.  It's important to call
     * this method before using other methods of the service.
     */
    void init();

    /**
     * Retrieves the name of the currently managed table.
     * @return The name of the table, or null if no table has been set.
     */
    String getTableName();

    /**
     * Creates a new table with the specified name and columns.
     * @param tableName The name of the new table. Should not be null or empty.
     * @param columns A map where keys represent column names and values represent SQL data types
     *                (e.g., "VARCHAR(255)", "INTEGER").  Should not be null or empty.
     * @throws BusinessException if an error occurs during table creation (e.g., table already exists,
     *                            invalid table name, invalid column definitions).
     */
    void create(String tableName, Map<String, String> columns) throws BusinessException;

    /**
     * Adds a new column to the managed table.
     * @param columnName The name of the new column. Should not be null or empty.
     * @param dataType The SQL data type of the new column (e.g., "VARCHAR(255)", "INTEGER"). Should not be null or empty.
     * @param constraints Optional constraints for the new column (e.g., "NOT NULL", "PRIMARY KEY", "UNIQUE").
     * @throws BusinessException if an error occurs during column addition (e.g., column already exists,
     *                            invalid column name, invalid data type, invalid constraints).
     */
    void addColumn(String columnName, String dataType, String... constraints) throws BusinessException;


    /**
     * Removes a column from the managed table.
     * @param columnName The name of the column to remove.  Should not be null or empty.
     * @throws BusinessException if an error occurs during column removal (e.g., column does not exist).
     */
    void removeColumn(String columnName) throws BusinessException;

    /**
     * Renames a column in the managed table.
     * @param columnName The current name of the column. Should not be null or empty.
     * @param newName    The new name for the column. Should not be null or empty.
     * @throws BusinessException if an error occurs during column renaming (e.g., column does not exist,
     *                            new name is invalid or already in use).
     */
    void renameColumn(String columnName, String newName) throws BusinessException;

    /**
     * Modifies the constraints of an existing column.
     * @param columnName The name of the column to modify.  Should not be null or empty.
     * @param constraints The new constraints for the column (e.g., "NOT NULL", "PRIMARY KEY", "UNIQUE").
     * @throws BusinessException if an error occurs during column modification (e.g., column does not exist,
     *                            invalid constraints).
     */
    void editColumn(String columnName, String... constraints) throws BusinessException;

    /**
     * Retrieves the properties of the managed table (e.g., table name, create date, etc.).
     * @return A map containing table properties, where keys are property names and values are property values.
     *         May return an empty map if no properties are available.
     * @throws BusinessException if an error occurs during property retrieval.
     */
    Map<String, String> getTableProperties() throws BusinessException;

    /**
     * Retrieves the columns of the managed table along with their data types.
     * @return A map where keys are column names and values are SQL data types (e.g., "VARCHAR(255)", "INTEGER").
     *         May return an empty map if the table has no columns.
     * @throws BusinessException if an error occurs during column retrieval.
     */
    Map<String, String> getTableColumns() throws BusinessException;
}
