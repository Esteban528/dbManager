package com.javadbmanager.data;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * DataAccessObjectImpl
 */
public class DataAccessObjectImpl implements DataAccessObject {

  private Connection connection;

  public DataAccessObjectImpl(Connection connection) {
    this.connection = connection;
  }

  public int executeUpdate(String sql) throws SQLException {
    int logs = 0;

    Statement statement = this.connection.createStatement();
    logs = statement.executeUpdate(sql);
    return logs;
  }

  @Override
  public ResultSet executeQuery(String sql) throws SQLException {

    Statement statement = this.connection.createStatement();
    ResultSet rs = statement.executeQuery(sql);

    return rs;
  }

  @Override
  public Connection getConnection() {
    return connection;
  }

  @Override
  public void setConnection(Connection connection) {
    this.connection = connection;
  }

  @Override
  public DatabaseMetaData getMetadata() throws SQLException {
    return connection.getMetaData();
  }
}
