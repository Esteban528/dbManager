
package com.javadbmanager.dataTest.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.javadbmanager.data.exceptions.ColumnNotFoundException;
import com.javadbmanager.data.utils.QueryBuilderImpl;

public class QueryBuilderTest {

    private final QueryBuilderImpl queryBuilder = new QueryBuilderImpl();

    @Test
    void makeInsertTest() throws ColumnNotFoundException {
        String tableName = "exampleTable";
        Map<String, String> items = new HashMap<>();
        Map<String, String> columnsTable = new HashMap<>();

        columnsTable.put("username", "VARCHAR(50)");
        columnsTable.put("rank", "VARCHAR(20)");
        columnsTable.put("experience", "INT(11)");

        items.put("username", "estebandev");
        items.put("rank", "JavaDeveloper");
        items.put("experience", "3");

        String query = queryBuilder.makeInsert(tableName, items, columnsTable);
        String expectedQuery = "INSERT INTO exampleTable (rank, experience, username) VALUES ('JavaDeveloper', '3', 'estebandev')";
        assertEquals(expectedQuery, query);

        columnsTable.clear();
        assertThrows(ColumnNotFoundException.class, () -> queryBuilder.makeInsert(tableName, items, columnsTable));
    }

    @Test
    void makeSelectTest() {
        String tableName = "exampleTable";
        Map<String, String> wheres = new HashMap<>();
        Map<String, String> columnsTable = new HashMap<>();

        columnsTable.put("id", "INT");
        columnsTable.put("username", "VARCHAR(50)");

        wheres.put("id", "5");

        String query = queryBuilder.makeSelect(tableName, wheres, columnsTable);
        String expectedQuery = "SELECT * FROM exampleTable WHERE id = '5'";
        assertEquals(expectedQuery, query);

        query = queryBuilder.makeSelect(tableName, new HashMap<>(), columnsTable);
        assertEquals("SELECT * FROM exampleTable", query);

        columnsTable.clear();
        assertThrows(ColumnNotFoundException.class, () -> queryBuilder.makeSelect(tableName, wheres, columnsTable));
    }

    @Test
    void makeUpdateTest() {
        String tableName = "exampleTable";
        Map<String, String> values = new HashMap<>();
        Map<String, String> wheres = new HashMap<>();
        Map<String, String> columnsTable = new HashMap<>();

        columnsTable.put("username", "VARCHAR(50)");
        columnsTable.put("experience", "INT(11)");

        values.put("username", "estebandev");
        wheres.put("experience", "20");

        String query = queryBuilder.makeUpdate(tableName, values, wheres, columnsTable);
        String expectedQuery = "UPDATE exampleTable SET username = 'estebandev' WHERE experience = '20'";
        assertEquals(expectedQuery, query);

        columnsTable.clear();
        assertThrows(ColumnNotFoundException.class, () -> queryBuilder.makeUpdate(tableName, values, wheres, columnsTable));
    }

    @Test
    void makeDeleteTest() {
        String tableName = "exampleTable";
        Map<String, String> wheres = new HashMap<>();
        Map<String, String> columnsTable = new HashMap<>();

        columnsTable.put("username", "VARCHAR(50)");

        wheres.put("username", "estebandev");

        String query = queryBuilder.makeDelete(tableName, wheres, columnsTable);
        String expectedQuery = "DELETE FROM exampleTable WHERE username = 'estebandev'";
        assertEquals(expectedQuery, query);

        columnsTable.clear();
        assertThrows(ColumnNotFoundException.class, () -> queryBuilder.makeDelete(tableName, wheres, columnsTable));
    }

    @Test
    void makeCreateTableTest() {
        String tableName = "exampleTable";
        Map<String, String> columnsTable = new HashMap<>();

        columnsTable.put("id", "INT PRIMARY KEY");
        columnsTable.put("username", "VARCHAR(50) NOT NULL");

        String query = queryBuilder.makeCreateTable(tableName, columnsTable);
        String expectedQuery = "CREATE TABLE IF NOT EXISTS exampleTable (id INT PRIMARY KEY, username VARCHAR(50) NOT NULL)";
        assertEquals(expectedQuery, query);
    }

    @Test
    void makeCreateTableColumnTest() {
        String tableName = "exampleTable";
        String query = queryBuilder.makeCreateTableColumn(tableName, "email", "VARCHAR(60)", "NOT NULL", "UNIQUE");
        String expectedQuery = "ALTER TABLE exampleTable ADD email VARCHAR(60) NOT NULL UNIQUE";
        assertEquals(expectedQuery, query);
    }

    @Test
    void makeDropTableTest() {
        String tableName = "exampleTable";
        String query = queryBuilder.makeDropTable(tableName);
        String expectedQuery = "DROP TABLE exampleTable";
        assertEquals(expectedQuery, query);
    }

    @Test
    void makeDropColumnTest() {
        String tableName = "exampleTable";
        String column = "username";
        String query = queryBuilder.makeDropColumn(tableName, column);
        String expectedQuery = "ALTER TABLE exampleTable DROP COLUMN username";
        assertEquals(expectedQuery, query);
    }

    @Test
    void makeAlterTableTest() {
        String tableName = "exampleTable";
        String column = "username";
        String query = queryBuilder.makeAlterTable(tableName, column, "VARCHAR(50)", "NOT NULL");
        String expectedQuery = "ALTER TABLE exampleTable MODIFY COLUMN username VARCHAR(50) NOT NULL";
        assertEquals(expectedQuery, query);
    }

    @Test
    void makeSelectWithLimitTest() {
        String tableName = "exampleTable";
        int limit = 10;
        String query = queryBuilder.makeSelectWithLimit(tableName, limit);
        String expectedQuery = "SELECT * FROM exampleTable LIMIT 10";
        assertEquals(expectedQuery, query);
    }

    @Test
    void makeRenameColumnTest() {
        String tableName = "exampleTable";
        String columnName = "oldColumn";
        String newName = "newColumn";
        String query = queryBuilder.makeRenameColumn(tableName, columnName, newName);
        String expectedQuery = "ALTER TABLE exampleTable RENAME COLUMN oldColumn TO newColumn;";
        assertEquals(expectedQuery, query);
    }
}

