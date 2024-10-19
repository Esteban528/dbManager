package com.javadbmanager.dataTest;

import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.javadbmanager.data.TableHandlerImpl;
import com.javadbmanager.data.utils.DataUtils;
import com.javadbmanager.data.utils.QueryBuilder;
import com.javadbmanager.data.utils.QueryBuilderImpl;
import com.javadbmanager.data.ConnectionHandler;

public class TableHandlerImplTest {

  private ConnectionHandler connectionHandler;
  private QueryBuilderImpl queryBuilderImpl = new QueryBuilderImpl();
  private DataUtils dataUtils;
  private String tableName = "users";

  @BeforeEach
  public void setUp() throws SQLException {
    connectionHandler = mock(ConnectionHandler.class);
    dataUtils = mock(DataUtils.class);
    Connection con = DriverManager.getConnection("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");
    when(connectionHandler.getConnection()).thenReturn(con);
  }

  @AfterEach
  public void tearDown() throws SQLException {
    try (Statement stmt = connectionHandler.getConnection().createStatement()) {
      stmt.execute("DROP TABLE IF EXISTS " + tableName);
    }
    connectionHandler.getConnection().close();
  }

  @Test
  void createTableTest() throws SQLException {
    TableHandlerImpl tableHandler = new TableHandlerImpl(connectionHandler, queryBuilderImpl, dataUtils);
    Map<String, String> columns = new HashMap<>();
    columns.put("id", "INT");
    columns.put("name", "VARCHAR(60)");

    tableHandler.createTable(tableName, columns);
    List<String> tables = getDBTables(connectionHandler.getConnection());

    assertTrue(tables.contains(tableName));
  }

  @Test
  void createColumnTest() throws SQLException {
    createTableWithSingleColumn("id INT");

    TableHandlerImpl tableHandler = new TableHandlerImpl(connectionHandler, queryBuilderImpl, dataUtils);
    String columnName = "name";

    tableHandler.createColumn(tableName, columnName, "VARCHAR(50)", "NOT NULL");

    assertColumnExists(columnName);
  }

  @Test
  void removeColumnTest() throws SQLException {
    createTableWithSingleColumn("id INT, name VARCHAR(60)");

    TableHandlerImpl tableHandler = new TableHandlerImpl(connectionHandler, queryBuilderImpl, dataUtils);
    String columnName = "name";

    tableHandler.removeColumn(tableName, columnName);

    assertThrows(SQLException.class, () -> insertUser(1, "estebandev"));
  }

  // @Test This test is bad make
  // void editColumnTest() throws SQLException {
  // createTableWithSingleColumn("id INT, name VARCHAR(60)");
  //
  // QueryBuilder queryBuilder = mock(QueryBuilderImpl.class);
  //
  // TableHandlerImpl tableHandler = new TableHandlerImpl(connectionHandler,
  // queryBuilderImpl, dataUtils);
  // String columnName = "name";
  //
  // when(queryBuilder.makeAlterTable(tableName, columnName, "username",
  // "VARCHAR(30)"))
  // .thenReturn("ALTER TABLE users ALTER COLUMN name RENAME TO username; " +
  // "ALTER TABLE users ALTER COLUMN username SET DATA TYPE VARCHAR(30);");
  //
  // tableHandler.editColumn(tableName, columnName, "username", "VARCHAR(30)");
  //
  // assertThrows(SQLException.class, () -> insertUser(1, "estebandev"));
  // }

  @Test
  void renameColumnTest() throws SQLException {
    createTableWithSingleColumn("id INT, name VARCHAR(60)");

    QueryBuilder queryBuilder = mock(QueryBuilderImpl.class);

    TableHandlerImpl tableHandler = new TableHandlerImpl(connectionHandler, queryBuilderImpl, dataUtils);
    String columnName = "name";

    when(queryBuilder.makeRenameColumn(tableName, columnName, "username")).thenReturn(
        "ALTER TABLE users ALTER COLUMN name RENAME TO username;");

    tableHandler.renameColumn(tableName, columnName, "username");

    assertThrows(SQLException.class, () -> insertUser(1, "estebandev"));
  }

  @Test
  @Disabled
  void removeTableTest() throws SQLException {
    createTableWithSingleColumn("id INT, name VARCHAR(60)");

    TableHandlerImpl tableHandler = new TableHandlerImpl(connectionHandler, queryBuilderImpl, dataUtils);

    tableHandler.removeTable(tableName);

    assertTrue(getDBTables(connectionHandler.getConnection()).isEmpty());
  }

  private void createTableWithSingleColumn(String columnsDefinition) throws SQLException {
    try (Statement stmt = connectionHandler.getConnection().createStatement()) {
      stmt.execute("CREATE TABLE " + tableName + " (" + columnsDefinition + ")");
    }
  }

  private void insertUser(int id, String name) throws SQLException {
    try (Statement stmt = connectionHandler.getConnection().createStatement()) {
      stmt.executeUpdate("INSERT INTO " + tableName + " (id, name) VALUES (" + id + ", '" + name + "')");
    }
  }

  private void assertColumnExists(String columnName) throws SQLException {
    try (Statement stmt = connectionHandler.getConnection().createStatement()) {
      stmt.executeUpdate("INSERT INTO " + tableName + " (id, " + columnName + ") VALUES (1, 'estebandev')");
      assertTrue(true);
    } catch (SQLException e) {
      fail("Column " + columnName + " does not exist.");
    }
  }

  private List<String> getDBTables(Connection con) throws SQLException {
    DatabaseMetaData metaData = con.getMetaData();
    ResultSet resultSet = metaData.getTables(null, null, "%", new String[] { "TABLE" });

    List<String> tables = new ArrayList<>();
    while (resultSet.next()) {
      tables.add(resultSet.getString("TABLE_NAME").toLowerCase());
    }
    return tables;
  }
}
