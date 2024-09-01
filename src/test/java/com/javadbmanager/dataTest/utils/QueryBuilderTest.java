package com.javadbmanager.dataTest.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

import com.javadbmanager.data.exceptions.ColumnNotFoundException;
import com.javadbmanager.data.utils.QueryBuilderImpl;

public class QueryBuilderTest {
  private QueryBuilderImpl queryBuilder = new QueryBuilderImpl();

  @Test
  void makeInsertTest() {
    String tableName = "exampleTable";
    HashMap<String, String> items = new HashMap<>();
    HashMap<String, String> columnsTable = new HashMap<>();

    columnsTable.put("username", "VARCHAR (50)");
    columnsTable.put("rank", "VARCHAR (20)");
    columnsTable.put("experience", "INT (11)");

    items.put("username", "estebandev");
    items.put("rank", "JavaDeveloper");
    items.put("experience", "3");

    String query = queryBuilder.makeInsert(tableName, items, columnsTable);

    assertEquals("INSERT INTO exampleTable (rank, experience, username) VALUES('JavaDeveloper', '3', 'estebandev')",
        query);

    columnsTable = new HashMap<>();
    boolean bool = false;
    try {
      query = queryBuilder.makeInsert(tableName, items, columnsTable);
    } catch (ColumnNotFoundException e) {
      bool = true;
    }

    assertTrue(bool);
  }

  @Test
  void makeSelectTest() {
    String tableName = "exampleTable";
    HashMap<String, String> whereMap = new HashMap<>();
    HashMap<String, String> columnsTable = new HashMap<>();

    columnsTable.put("id", "INT");
    columnsTable.put("username", "VARCHAR (50)");
    columnsTable.put("rank", "VARCHAR (20)");
    columnsTable.put("experience", "INT (11)");

    whereMap.put("id", "5");

    String query = queryBuilder.makeSelect(tableName, whereMap, columnsTable);
    String queryExpect = "SELECT * FROM exampleTable WHERE  id = '5'";

    assertTrue(query.contains(queryExpect));
    query = queryBuilder.makeSelect(tableName, new HashMap<>(), columnsTable);
    assertEquals("SELECT * FROM exampleTable  ", query);

    columnsTable = new HashMap<>();
    boolean bool = false;
    try {
      query = queryBuilder.makeSelect(tableName, whereMap, columnsTable);
    } catch (ColumnNotFoundException e) {
      bool = true;
    }

    assertTrue(bool);
  }

  @Test
  void makeUpdateTest() {
    String tableName = "exampleTable";
    HashMap<String, String> values = new HashMap<>();
    HashMap<String, String> whereMap = new HashMap<>();
    HashMap<String, String> columnsTable = new HashMap<>();

    columnsTable.put("id", "INT");
    columnsTable.put("username", "VARCHAR (50)");
    columnsTable.put("rank", "VARCHAR (20)");
    columnsTable.put("experience", "INT (11)");

    values.put("username", "estebandev");
    values.put("experience", "20");
    whereMap.put("id", "5");
    whereMap.put("rank", "JavaDeveloper");

    String query = queryBuilder.makeUpdate(tableName, values, whereMap, columnsTable);
    String queryExpect = "UPDATE exampleTable SET experience = '20'  , username = 'estebandev'   WHERE rank = 'JavaDeveloper'  AND id = '5'";
    assertTrue(query.contains(queryExpect));

    columnsTable = new HashMap<>();
    boolean bool = false;
    try {
      query = queryBuilder.makeUpdate(tableName, values, whereMap, columnsTable);
    } catch (ColumnNotFoundException e) {
      bool = true;
    }

    assertTrue(bool);
  }

  @Test
  void makeDeleteTest() {
    String tableName = "exampleTable";
    HashMap<String, String> whereMap = new HashMap<>();
    HashMap<String, String> columnsTable = new HashMap<>();

    columnsTable.put("id", "INT");
    columnsTable.put("username", "VARCHAR (50)");
    columnsTable.put("rank", "VARCHAR (20)");
    columnsTable.put("experience", "INT (11)");
    whereMap.put("id", "5");
    whereMap.put("rank", "JavaDeveloper");

    String query = queryBuilder.makeDelete(tableName, whereMap, columnsTable);
    String queryExpect = "DELETE exampleTable WHERE rank = 'JavaDeveloper' AND id = '5'";
    assertTrue(query.contains(queryExpect));

    columnsTable = new HashMap<>();
    boolean bool = false;
    try {
      query = queryBuilder.makeDelete(tableName, whereMap, columnsTable);
    } catch (ColumnNotFoundException e) {
      bool = true;
    }

    assertTrue(bool);
  }

  @Test
  void makeCreateTableTest() {
    String tableName = "exampleTable";
    HashMap<String, String> columnsTable = new HashMap<>();

    columnsTable.put("id", "INT PRIMARY KEY");
    columnsTable.put("username", " VARCHAR(60) NOT NULL UNIQUE");
    columnsTable.put("rank", "VARCHAR (50)");
    columnsTable.put("experience", "INT (11)");

    String query = queryBuilder.makeCreateTable(tableName, columnsTable);
    String queryExpect = "CREATE TABLE IF NOT EXISTS exampleTable (rank VARCHAR (50), id INT PRIMARY KEY, experience INT (11), username  VARCHAR(60) NOT NULL UNIQUE)";
    assertEquals(queryExpect, query);
  }

  @Test
  void makeCreateTableColumn() {
    String tableName = "exampleTable";

    String query = queryBuilder.makeCreateTableColumn(tableName, "email", "VARCHAR(60)", "NOT NULL", "UNIQUE");
    String queryExpect = "ALTER TABLE exampleTable ADD email VARCHAR(60) NOT NULL UNIQUE";
    assertTrue(query.contains(queryExpect));
  }

  @Test
  void makeDropTableTest() {
    String tableName = "exampleTable";

    String query = queryBuilder.makeDropTable(tableName);
    String queryExpect = "DROP TABLE exampleTable";
    assertTrue(query.contains(queryExpect));
  }

  @Test
  void makeDropColumnTest() {
    String tableName = "exampleTable";
    String columnName = "columnName";

    String query = queryBuilder.makeDropColumn(tableName, columnName);
    String queryExpect = "ALTER TABLE exampleTable DROP COLUMN columnName";
    assertTrue(query.contains(queryExpect));
  }

  @Test
  void makeAlterTableTest() {
    String tableName = "exampleTable";
    String columnName = "columnName";

    String query = queryBuilder.makeAlterTable(tableName, columnName, "INT (5)", "DEFAULT", "NOT NULL");
    String queryExpect = "ALTER TABLE exampleTable MODIFY COLUMN columnName INT (5) DEFAULT NOT NULL";
    assertTrue(query.contains(queryExpect));

  }
}
