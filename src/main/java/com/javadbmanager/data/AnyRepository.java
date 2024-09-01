package com.javadbmanager.data;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.javadbmanager.data.exceptions.ColumnNotFoundException;
import com.javadbmanager.data.utils.DataUtils;

public interface AnyRepository {

  String getTableName();

  void setTableName(String tableName, DataUtils dataUtils);

  int insert(Map<String, String> items) throws SQLException, ColumnNotFoundException;

  List<Map<String, String>> get() throws SQLException;

  List<Map<String, String>> get(Map<String, String> wheres) throws SQLException, ColumnNotFoundException;

  int update(Map<String, String> items, Map<String, String> wheres) throws SQLException, ColumnNotFoundException;

  int delete(Map<String, String> wheres) throws SQLException, ColumnNotFoundException;

  void commitChange() throws SQLException;

}
