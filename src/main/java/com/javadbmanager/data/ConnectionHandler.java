package com.javadbmanager.data;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionHandler {
  Connection getConnection() throws SQLException;

  void createConnection() throws SQLException;

  void close() throws SQLException;
}
