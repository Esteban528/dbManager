package com.javadbmanager.data;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface DataAccessObject {
  void setConnection(Connection connection);

  Connection getConnection();

  ResultSet executeQuery(String sql) throws SQLException;

  int executeUpdate(String sql) throws SQLException;

  DatabaseMetaData getMetadata() throws SQLException;
}
