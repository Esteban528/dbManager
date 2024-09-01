package com.javadbmanager.data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.javadbmanager.data.exceptions.ColumnNotFoundException;
import com.javadbmanager.data.utils.DataUtils;
import com.javadbmanager.data.utils.QueryBuilder;

public class AnyRepositoryImpl implements AnyRepository {
  private String tableName;
  private Connection connection;
  private Map<String, String> columns;
  private DataAccessObject dataAccessObject;
  private QueryBuilder queryBuilder;

  public AnyRepositoryImpl(ConnectionHandler connectionHandler, QueryBuilder queryBuilder,
      DataAccessObject dataAccessObject) throws SQLException {
    this(connectionHandler.getConnection(), queryBuilder, dataAccessObject);
  }

  public AnyRepositoryImpl(Connection connection, QueryBuilder queryBuilder, DataAccessObject dataAccessObject) {
    this.connection = connection;
    this.queryBuilder = queryBuilder;
    this.dataAccessObject = dataAccessObject;
    dataAccessObject.setConnection(connection);
  }

  @Override
  public String getTableName() {
    return tableName;
  }

  @Override
  public void setTableName(String tableName, DataUtils dataUtils) {
    this.tableName = tableName;
    this.columns = dataUtils.getColumnsData(connection, tableName);
  }

  @Override
  public int insert(Map<String, String> items) throws ColumnNotFoundException, SQLException {
    String sql = queryBuilder.makeInsert(this.tableName, items, this.columns);
    return dataAccessObject.executeUpdate(sql);
  }

  @Override
  public List<Map<String, String>> get() throws SQLException, ColumnNotFoundException {
    return get(new HashMap<>());
  }

  @Override
  public List<Map<String, String>> get(Map<String, String> wheres) throws SQLException, ColumnNotFoundException {
    String sql = queryBuilder.makeSelect(tableName, wheres, this.columns);
    ResultSet rs = dataAccessObject.executeQuery(sql);
    ArrayList<Map<String, String>> results = new ArrayList<>();

    while (rs.next()) {
      Map<String, String> columnResult = new HashMap<>();
      Iterator<String> iterator = this.columns.keySet().iterator();

      while (iterator.hasNext()) {
        String columnName = iterator.next();
        columnResult.put(columnName, rs.getString(columnName));
      }
      results.add(columnResult);
    }

    return results;
  }

  @Override
  public int update(Map<String, String> items, Map<String, String> wheres)
      throws SQLException, ColumnNotFoundException {
    String sql = queryBuilder.makeUpdate(tableName, items, wheres, columns);
    return dataAccessObject.executeUpdate(sql);
  }

  @Override
  public int delete(Map<String, String> wheres) throws SQLException, ColumnNotFoundException {
    String sql = queryBuilder.makeDelete(tableName, wheres, columns);
    return dataAccessObject.executeUpdate(sql);
  }

  @Override
  public void commitChange() throws SQLException {
    this.connection.commit();
  }

}
