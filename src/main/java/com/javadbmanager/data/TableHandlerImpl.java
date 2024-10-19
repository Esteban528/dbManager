package com.javadbmanager.data;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.javadbmanager.data.utils.DataUtils;
import com.javadbmanager.data.utils.QueryBuilder;

public class TableHandlerImpl implements TableHandler {
  private DataAccessObject dataAccessObject;

  private QueryBuilder queryBuilder;
  private DataUtils dataUtils;

  public TableHandlerImpl(ConnectionHandler connectionHandler, QueryBuilder queryBuilder, DataUtils dataUtils)
      throws SQLException {
    this(connectionHandler.getConnection(), queryBuilder);
    this.dataUtils = dataUtils;
  }

  public TableHandlerImpl(Connection connection, QueryBuilder queryBuilder) {
    this.dataAccessObject = new DataAccessObjectImpl(connection);
    this.queryBuilder = queryBuilder;
  }

  @Override
  public void createTable(String tableName, Map<String, String> columns) throws SQLException {
    String sql = queryBuilder.makeCreateTable(tableName, columns);
    dataAccessObject.executeUpdate(sql);
  }

  @Override
  public void createColumn(String tableName, String columnName, String dataType, String... constraints)
      throws SQLException {
    String sql = queryBuilder.makeCreateTableColumn(tableName, columnName, dataType, constraints);
    dataAccessObject.executeUpdate(sql);
  }

  @Override
  public void removeColumn(String tableName, String columnName) throws SQLException {
    String sql = queryBuilder.makeDropColumn(tableName, columnName);
    dataAccessObject.executeUpdate(sql);
  }

  @Override
  public void editColumn(String tableName, String columnName, String... constraints) throws SQLException {
    String sql = queryBuilder.makeAlterTable(tableName, columnName, constraints);
    dataAccessObject.executeUpdate(sql);
  }

  @Override
  public void removeTable(String tableName) throws SQLException {
    String sql = queryBuilder.makeDropTable(tableName);
    dataAccessObject.executeUpdate(sql);
  }

  @Override
  public void renameColumn(String tableName, String columnName, String newName) throws SQLException {
    String sql = queryBuilder.makeRenameColumn(tableName, columnName, newName);
    dataAccessObject.executeUpdate(sql);
  }

  @Override
  public Map<String, String> getTableProperties(String tableName) throws SQLException {
    Map<String, String> columnsMap = new HashMap<>();
    try {
      DatabaseMetaData metaData = dataAccessObject.getMetadata();

      try (ResultSet columns = metaData.getColumns(null, null, tableName, "%")) {
        while (columns.next()) {
          String columnName = columns.getString("COLUMN_NAME");
          String columnType = columns.getString("TYPE_NAME");
          int columnSize = columns.getInt("COLUMN_SIZE");
          int decimalDigits = columns.getInt("DECIMAL_DIGITS");
          int nullable = columns.getInt("NULLABLE");
          // String remarks = columns.getString("REMARKS");
          String columnDef = columns.getString("COLUMN_DEF");
          // String isNullable = columns.getString("IS_NULLABLE");
          String isAutoIncrement = columns.getString("IS_AUTOINCREMENT");
          String isGeneratedColumn = columns.getString("IS_GENERATEDCOLUMN");

          String columnDetails = String.format(
              "%s (%d, %d digits, Nullable: %s, Default: %s, AutoIncrement: %s, Generated: %s)",
              columnType, columnSize, decimalDigits,
              (nullable == DatabaseMetaData.columnNoNulls ? "NO" : "YES"),
              columnDef != null ? columnDef : "None",
              isAutoIncrement.equals("YES") ? "YES" : "NO",
              isGeneratedColumn.equals("YES") ? "YES" : "NO");

          columnsMap.put(columnName, columnDetails);
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return columnsMap;
  }

  @Override
  public Map<String, String> getTableColumns(String tableName) throws SQLException {
    return dataUtils.getColumnsData(dataAccessObject.getConnection(), tableName);
  }
}
