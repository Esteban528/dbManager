package com.javadbmanager.data.utils;

import java.sql.Connection;
import java.util.Map;

import com.javadbmanager.data.ConnectionBean;

/**
 * The {@code DataUtils} interface provides utility methods for working with data,
 * including constructing database connection URLs and retrieving column metadata.
 */
public interface DataUtils {

    /**
     * Constructs a JDBC connection URL based on the provided {@code ConnectionBean}.
     * @param connectionBean The connection bean containing the connection parameters.
     * @return The formatted JDBC connection URL.
     */
    String makeUrl(ConnectionBean connectionBean);

    /**
     * Retrieves metadata about the columns of a specified table.
     * @param connection An active database connection.
     * @param tableName The name of the table.
     * @return A map where keys are column names and values are their respective data types.
     * @throws SQLException if a database access error occurs or the table does not exist.  (You would need to add the throws declaration.)
     */
    Map<String, String> getColumnsData(Connection connection, String tableName);
}
