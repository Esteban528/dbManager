package com.javadbmanager.dataTest;

import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.javadbmanager.data.AnyRepositoryImpl;
import com.javadbmanager.data.ConnectionHandler;
import com.javadbmanager.data.DataAccessObject;
import com.javadbmanager.data.DataAccessObjectImpl;
import com.javadbmanager.data.exceptions.ColumnNotFoundException;
import com.javadbmanager.data.utils.DataUtils;
import com.javadbmanager.data.utils.DataUtilsImpl;
import com.javadbmanager.data.utils.QueryBuilderImpl;

public class AnyRepositoryImplTest {

  ConnectionHandler connectionHandler;
  DataUtils dataUtils;
  QueryBuilderImpl queryBuilder = new QueryBuilderImpl();
  DataAccessObject dataAccessObject;
  String tableName = "users";
  boolean state = false;

  @BeforeEach
  public void setUp() throws SQLException {
    connectionHandler = mock(ConnectionHandler.class);
    dataUtils = mock(DataUtilsImpl.class);

    Map<String, String> columnData = new HashMap<>();
    columnData.put("name", "VARCHAR(255)");
    Connection con = DriverManager.getConnection("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");

    when(connectionHandler.getConnection()).thenReturn(con);

    when(dataUtils.getColumnsData(connectionHandler.getConnection(), "users")).thenReturn(
        columnData);
    connectionHandler.getConnection().setAutoCommit(false);
    try (Statement stmt = connectionHandler.getConnection().createStatement()) {
      stmt.execute("CREATE TABLE users (id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(255))");
    }

    dataAccessObject = new DataAccessObjectImpl(connectionHandler.getConnection());
  }

  @AfterEach
  public void tearDown() throws SQLException {
    try (Statement stmt = connectionHandler.getConnection().createStatement()) {
      stmt.execute("DROP TABLE users");
    }
    connectionHandler.getConnection().close();
  }

  @Test
  public void insertAndGet() throws SQLException {
    AnyRepositoryImpl anyRepositoryImpl = new AnyRepositoryImpl(connectionHandler, queryBuilder, dataAccessObject);
    anyRepositoryImpl.setTableName(tableName, dataUtils);
    String expectKey = "name";
    String expectValue = "EstebanDev";

    HashMap<String, String> items = new HashMap<>();
    items.put(expectKey, expectValue);
    anyRepositoryImpl.insert(items);

    List<Map<String, String>> results = anyRepositoryImpl.get();

    results.forEach(values -> {
      Iterator<String> iterator = values.keySet().iterator();

      String key = iterator.next();
      String value = values.get(key);

      assertEquals(expectKey, key);
      assertEquals(expectValue, value);
    });
  }

  @Test
  public void insertAndGetWhereTest() throws SQLException {
    AnyRepositoryImpl anyRepositoryImpl = new AnyRepositoryImpl(connectionHandler, queryBuilder, dataAccessObject);
    anyRepositoryImpl.setTableName(tableName, dataUtils);

    String expectKey = "name";
    String expectValue = "EstebanDev";

    HashMap<String, String> items = new HashMap<>();
    items.put(expectKey, expectValue);
    anyRepositoryImpl.insert(items);

    Map<String, String> where = new HashMap<>();
    where.put(expectKey, expectValue);

    List<Map<String, String>> results = anyRepositoryImpl.get(where);
    setState(false);

    results.forEach(values -> {
      Iterator<String> iterator = values.keySet().iterator();

      String key = iterator.next();
      String value = values.get(key);

      assertEquals(expectKey, key);
      assertEquals(expectValue, value);
      setState(true);
    });

    assertThrows(ColumnNotFoundException.class, () -> {
      Map<String, String> whereMap = new HashMap<>();
      whereMap.put("id", "5");
      anyRepositoryImpl.get(whereMap);
    });

    where = new HashMap<>();
    where.put(expectKey, "Bill");

    setState(true);

    results = anyRepositoryImpl.get(where);
    results.forEach(values -> {
      setState(false);
    });
    assertTrue(this.state);
  }

  @Test
  public void updateAndGetTest() throws SQLException {
    AnyRepositoryImpl anyRepositoryImpl = new AnyRepositoryImpl(connectionHandler, queryBuilder, dataAccessObject);
    anyRepositoryImpl.setTableName(tableName, dataUtils);

    try (Statement stmt = connectionHandler.getConnection().createStatement()) {
      stmt.execute("INSERT INTO users SET name='Bill'");
    }

    String expectKey = "name";
    String expectValue = "EstebanDev";

    HashMap<String, String> items = new HashMap<>();
    items.put(expectKey, expectValue);

    HashMap<String, String> where = new HashMap<>();
    where.put("name", "Bill");

    anyRepositoryImpl.update(items, where);

    setState(false);
    List<Map<String, String>> results = anyRepositoryImpl.get();
    results.forEach(values -> {
      Iterator<String> iterator = values.keySet().iterator();

      String key = iterator.next();
      String value = values.get(key);

      setState(true);

      assertEquals(expectValue, value);
    });
    assertTrue(this.state);
  }

  @Test
  public void deleteAndGetTest() throws SQLException {
    AnyRepositoryImpl anyRepositoryImpl = new AnyRepositoryImpl(connectionHandler, queryBuilder, dataAccessObject);
    anyRepositoryImpl.setTableName(tableName, dataUtils);

    try (Statement stmt = connectionHandler.getConnection().createStatement()) {
      stmt.execute("INSERT INTO users SET name='Bill'");
    }

    HashMap<String, String> where = new HashMap<>();
    where.put("name", "Bill");
    anyRepositoryImpl.delete(where);

    List<Map<String, String>> results = anyRepositoryImpl.get();
    assertTrue(results.isEmpty());
  }

  @Test
  public void commitChange() throws SQLException {
    Connection con = connectionHandler.getConnection();
    con.setAutoCommit(false);
    assertTrue(!con.getAutoCommit());

    try (Statement stmt = con.createStatement()) {
      stmt.execute("INSERT INTO users SET name='Bill'");
      stmt.execute("INSERT INTO users SET name='Bob'");
      stmt.execute("INSERT INTO users SET name='Ana'");
    }

    try {
      AnyRepositoryImpl anyRepositoryImpl = new AnyRepositoryImpl(con, queryBuilder, dataAccessObject);
      anyRepositoryImpl.setTableName(tableName, dataUtils);

      //
      HashMap<String, String> where = new HashMap<>();
      where.put("name", "Bill");
      anyRepositoryImpl.delete(where);

      //
      HashMap<String, String> items = new HashMap<>();
      items.put("name", "Esteban");
      anyRepositoryImpl.insert(items);

      //
      items = new HashMap<>();
      items.put("name", "Marlon");
      anyRepositoryImpl.insert(items);

      //
      items = new HashMap<>();
      items.put("name", "Bob Esponja");

      where = new HashMap<>();
      where.put("name", "Bob");
      anyRepositoryImpl.update(items, where);

      anyRepositoryImpl.commitChange();

      String[] expectArray = { "name Bob Esponja", "name Ana", "name Esteban", "name Marlon" };
      List<String> expected = Arrays.asList(expectArray);
      List<String> actual = new ArrayList<>();

      anyRepositoryImpl.get().forEach(value -> {
        for (String key : value.keySet()) {
          String line = (String.format("%s %s", key, value.get(key)));
          actual.add(line);
        }
      });

      assertEquals(expected, actual);
    } catch (SQLException e) {
      try {
        con.rollback();
      } catch (SQLException eRSqlException) {
        eRSqlException.printStackTrace();
      }
    }
  }

  void setState(boolean bool) {
    this.state = bool;
  }
}
