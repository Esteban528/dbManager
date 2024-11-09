package com.javadbmanager.data.utils;

import java.util.Map;
import java.util.stream.Collectors;
import com.javadbmanager.data.exceptions.ColumnNotFoundException;

public class QueryBuilderImpl implements QueryBuilder {

    public String makeInsert(String tableName, Map<String, String> items, Map<String, String> columnsTable)
            throws ColumnNotFoundException {
        validateColumns(items.keySet(), columnsTable, tableName);

        String columns = String.join(", ", items.keySet());
        String values = items.values().stream()
                .map(value -> String.format("'%s'", value))
                .collect(Collectors.joining(", "));

        return String.format("INSERT INTO %s (%s) VALUES (%s)", tableName, columns, values);
    }

    public String makeSelect(String tableName, Map<String, String> wheres, Map<String, String> columnsTable) {
        validateColumns(wheres.keySet(), columnsTable, tableName);

        String whereClause = makeWhereClause(wheres);
        return String.format("SELECT * FROM %s%s", tableName, whereClause);
    }

    public String makeUpdate(String tableName, Map<String, String> items, Map<String, String> wheres,
            Map<String, String> columnsTable) {
        validateColumns(items.keySet(), columnsTable, tableName);
        validateColumns(wheres.keySet(), columnsTable, tableName);

        String setClause = items.entrySet().stream()
                .map(entry -> String.format("%s = '%s'", entry.getKey(), entry.getValue()))
                .collect(Collectors.joining(", "));

        String whereClause = makeWhereClause(wheres);
        return String.format("UPDATE %s SET %s%s", tableName, setClause, whereClause);
    }

    public String makeDelete(String tableName, Map<String, String> wheres, Map<String, String> columnsTable) {
        validateColumns(wheres.keySet(), columnsTable, tableName);

        String whereClause = makeWhereClause(wheres);
        return String.format("DELETE FROM %s%s", tableName, whereClause);
    }

    public String makeCreateTable(String tableName, Map<String, String> columns) {
        String columnsDefinition = columns.entrySet().stream()
                .map(entry -> entry.getKey() + " " + entry.getValue())
                .collect(Collectors.joining(", "));

        return String.format("CREATE TABLE IF NOT EXISTS %s (%s)", tableName, columnsDefinition);
    }

    public String makeCreateTableColumn(String tableName, String columnName, String dataType, String... constraints) {
        String constraintClause = String.join(" ", constraints);
        return String.format("ALTER TABLE %s ADD %s %s %s", tableName, columnName, dataType, constraintClause);
    }

    public String makeDropTable(String tableName) {
        return String.format("DROP TABLE %s", tableName);
    }

    public String makeDropColumn(String tableName, String column) {
        return String.format("ALTER TABLE %s DROP COLUMN %s", tableName, column);
    }

    public String makeAlterTable(String tableName, String column, String... constraints) {
        String constraintClause = String.join(" ", constraints);
        return String.format("ALTER TABLE %s MODIFY COLUMN %s %s", tableName, column, constraintClause);
    }

    public String makeSelectWithLimit(String tableName, int limit) {
        return String.format("SELECT * FROM %s LIMIT %d", tableName, limit);
    }

    public String makeRenameColumn(String tableName, String columnName, String newName) {
        return String.format("ALTER TABLE %s RENAME COLUMN %s TO %s;", tableName, columnName, newName);
    }

    private String makeWhereClause(Map<String, String> wheres) {
        if (wheres.isEmpty())
            return "";
        return " WHERE " + wheres.entrySet().stream()
                .map(entry -> String.format("%s = '%s'", entry.getKey(), entry.getValue()))
                .collect(Collectors.joining(" AND "));
    }

    private void validateColumns(Iterable<String> keys, Map<String, String> columnsTable, String tableName)
            throws ColumnNotFoundException {
        for (String key : keys) {
            if (!columnsTable.containsKey(key)) {
                throw new ColumnNotFoundException(key, tableName);
            }
        }
    }
}
