package com.javadbmanager.data;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * The {@code ConnectionHandler} interface defines methods for managing a database connection.
 * Implementations of this interface are responsible for creating, retrieving, and closing
 * connections to the database.
 */
public interface ConnectionHandler {

    /**
     * Retrieves the current database connection.
     * @return The active {@code Connection} object.
     * @throws SQLException if a database access error occurs or the connection is invalid.
     */
    Connection getConnection() throws SQLException;

    /**
     * Creates a new database connection.  If a connection already exists, the behavior
     * is implementation-specific (it might close the existing connection and create a new
     * one, or it might reuse the existing connection).
     * @throws SQLException if a database access error occurs during connection creation.
     */
    void createConnection() throws SQLException;

    /**
     * Closes the current database connection.
     * @throws SQLException if a database access error occurs during connection closure.
     */
    void close() throws SQLException;
}
