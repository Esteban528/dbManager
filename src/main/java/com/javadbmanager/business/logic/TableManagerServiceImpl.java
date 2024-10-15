package com.javadbmanager.business.logic;

import java.sql.SQLException;
import java.util.Map;

import com.javadbmanager.business.logic.exceptions.BusinessException;
import com.javadbmanager.data.TableHandler;

public class TableManagerServiceImpl implements TableManagerService {
  private String tableName;
  private TableHandler tableHandler;
  private final DataLayerProvider dataLayerProvider;

  public TableManagerServiceImpl(DataLayerProvider dataLayerProvider) {
    this.dataLayerProvider = dataLayerProvider;
  }

  public void init() {
    this.tableHandler = dataLayerProvider.getTableHandler();
  }

  @Override
  public String getTableName() {
    return tableName;
  }

  @Override
  public void setTableName(String tableName) {
    this.tableName = tableName;
  }

  @Override
  public void create(String tableName, Map<String, String> columns) throws BusinessException {
    setTableName(tableName);

    try {
      tableHandler.createTable(tableName, columns);
    } catch (SQLException e) {
      throw new BusinessException(e.getMessage(), e);
    }
  }

  @Override
  public void addColumn(String columnName, String dataType, String... constraints) throws BusinessException {
    try {
      tableHandler.createColumn(tableName, columnName, dataType, constraints);
    } catch (SQLException e) {
      throw new BusinessException(e.getMessage(), e);
    }
  }

  @Override
  public void removeColumn(String columnName) throws BusinessException {
    try {
      tableHandler.removeColumn(tableName, columnName);
    } catch (SQLException e) {
      throw new BusinessException(e.getMessage(), e);
    }
  }

  @Override
  public void renameColumn(String columnName, String newName) throws BusinessException {
    try {
      tableHandler.renameColumn(tableName, columnName, newName);
    } catch (SQLException e) {
      throw new BusinessException(e.getMessage(), e);
    }
  }

  @Override
  public void editColumn(String columnName, String... constraints) throws BusinessException {
    try {
      tableHandler.editColumn(tableName, columnName, constraints);
    } catch (SQLException e) {
      throw new BusinessException(e.getMessage(), e);
    }
  }

  @Override
  public Map<String, String> getTableProperties() throws BusinessException {
    try {
      Map<String, String> result = tableHandler.getTableProperties(tableName);
      return result;
    } catch (SQLException e) {
      // e.printStackTrace();
      throw new BusinessException(e.getMessage(), e);
    }
  }

  @Override
  public Map<String, String> getTableColumns() throws BusinessException {
    try {
      Map<String, String> result = tableHandler.getTableColumns(tableName);
      return result;
    } catch (SQLException e) {
      // e.printStackTrace();
      throw new BusinessException(e.getMessage(), e);
    }
  }
}
