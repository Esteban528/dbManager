package com.javadbmanager.dataTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.javadbmanager.data.DataAccessObjectImpl;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class DataAccessObjectImplTest {

  private DataAccessObjectImpl dao;
  private Connection connection;
  private Statement statement;
  private ResultSet resultSet;
  private DatabaseMetaData databaseMetaData;

  @BeforeEach
  void setUp() throws SQLException {
    connection = mock(Connection.class);
    statement = mock(Statement.class);
    resultSet = mock(ResultSet.class);
    databaseMetaData = mock(DatabaseMetaData.class);

    when(connection.createStatement()).thenReturn(statement);
    dao = new DataAccessObjectImpl(connection);
  }

  @Test
  void testExecuteUpdate() throws SQLException {
    String sql = "UPDATE table_name SET column_name = 'value' WHERE condition";
    int expectedLogs = 1;

    when(statement.executeUpdate(sql)).thenReturn(expectedLogs);

    int actualLogs = dao.executeUpdate(sql);

    assertEquals(expectedLogs, actualLogs);
    verify(statement, times(1)).executeUpdate(sql);
  }

  @Test
  void testExecuteQuery() throws SQLException {
    String sql = "SELECT * FROM table_name";

    when(statement.executeQuery(sql)).thenReturn(resultSet);

    ResultSet actualResultSet = dao.executeQuery(sql);

    assertEquals(resultSet, actualResultSet);
    verify(statement, times(1)).executeQuery(sql);
  }

  @Test
  void testGetConnection() {
    assertEquals(connection, dao.getConnection());
  }

  @Test
  void testSetConnection() {
    Connection newConnection = mock(Connection.class);
    dao.setConnection(newConnection);
    assertEquals(newConnection, dao.getConnection());
  }

  @Test
  void testGetMetadata() throws SQLException {
    when(connection.getMetaData()).thenReturn(databaseMetaData);

    DatabaseMetaData actualMetadata = dao.getMetadata();

    assertEquals(databaseMetaData, actualMetadata);
    verify(connection, times(1)).getMetaData();
  }
}
