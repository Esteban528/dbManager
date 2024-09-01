package com.javadbmanager.data.utils;

import java.sql.Connection;
import java.util.Map;

import com.javadbmanager.data.ConnectionBean;

public interface DataUtils {

  String makeUrl(ConnectionBean connectionBean);

  Map<String, String> getColumnsData(Connection connection, String tableName);
}
