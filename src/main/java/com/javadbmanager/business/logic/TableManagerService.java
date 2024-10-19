package com.javadbmanager.business.logic;

import java.util.Map;

import com.javadbmanager.business.logic.exceptions.BusinessException;

public interface TableManagerService {
  void setTableName(String tableName);

  void init();

  String getTableName();

  void create(String tableName, Map<String, String> columns) throws BusinessException;

  void addColumn(String columnName, String dataType, String... constraints) throws BusinessException;

  void removeColumn(String columnName) throws BusinessException;

  void renameColumn(String columnName, String newName) throws BusinessException;

  void editColumn(String columnName, String... constraints) throws BusinessException;

  Map<String, String> getTableProperties() throws BusinessException;

  Map<String, String> getTableColumns() throws BusinessException;
}
