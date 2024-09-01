package com.javadbmanager.business.logic;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.javadbmanager.business.logic.exceptions.BusinessException;
import com.javadbmanager.data.AnyRepository;

public interface DataService {

  void setTableName(String tableName);

  String getTableName();

  int insert(Map<String, String> items) throws BusinessException;

  List<Map<String, String>> get() throws BusinessException;

  List<Map<String, String>> get(Map<String, String> where) throws BusinessException;

  int update(Map<String, String> items, Map<String, String> wheres) throws BusinessException;

  int delete(Map<String, String> where) throws BusinessException;

  void commit() throws BusinessException;

  void commitAndClose() throws BusinessException;

  AnyRepository getAnyRepository();
}
