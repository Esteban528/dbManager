package com.javadbmanager.data;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This interface defines the methods for interacting with a database.
 * 
 * @author Your Name
 */
public interface DataAccessObject {
  /**
   * Sets the connection to the database.
   * 
   * @param connection The database connection.
   */
  void setConnection(Connection connection);

  /**
   * Gets the current database connection.
   * 
   * @return The database connection.
   */
  Connection getConnection();

  /**
   * Executes a SQL query and returns the result set.
   * 
   * @param sql The SQL query to execute.
   * @return The result set.
   * @throws SQLException If an error occurs while executing the query.
   */
  ResultSet executeQuery(String sql) throws SQLException;

  /**
   * Executes an SQL update statement and returns the number of rows affected.
   * 
   * @param sql The SQL update statement.
   * @return The number of rows affected.
   * @throws SQLException If an error occurs while executing the update.
   */
  int executeUpdate(String sql) throws SQLException;

  /**
   * Retrieves metadata about the database.
   * 
   * @return The database metadata.
   * @throws SQLException If an error occurs while retrieving the metadata.
   */
  DatabaseMetaData getMetadata() throws SQLException;
}
