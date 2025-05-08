package com.javadbmanager.data.utils;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.javadbmanager.data.ConnectionBean;

public class DataUtilsImpl implements DataUtils {
    private final Map<String, String> databaseEngineMap = Map.of(
            "mysql", "jdbc:mysql://%s:%s/%s?useSSL=false&%s",
            "mysql_1", "jdbc:mysql://%s:%s?useSSL=false&%s",
            "postgresql", "jdbc:postgresql://%s:%s/%s?ssl=false&%s"
        );

    @Override
    public String makeUrl(ConnectionBean connectionBean) {
        String url, properties = "&useTimezone=true&serverTimezone=UTC";

        if (connectionBean.getDBType().equalsIgnoreCase("mysql") && connectionBean.getDBversion() >= 8) {
            properties += "&allowPublicKeyRetrieval=true";
        }

        url = String.format(
            databaseEngineMap.getOrDefault(connectionBean.getDatabase().isEmpty() ? "mysql_1" 
                : connectionBean.getDBType(), databaseEngineMap.get("mysql")),
            connectionBean.getHost(),
            connectionBean.getPort(),
            connectionBean.getDatabase().isEmpty() ? properties : connectionBean.getDatabase(),
            properties
        );

        return url;
    }

    @Override
    public Map<String, String> getColumnsData(Connection connection, String tableName) {
        Map<String, String> columnMap = new HashMap<>();

        try {
            DatabaseMetaData metaData = (DatabaseMetaData) connection.getMetaData();

            try (ResultSet columns = metaData.getColumns(null, null, tableName, null)) {
                while (columns.next()) {
                    String columnName = columns.getString("COLUMN_NAME");
                    String columnType = columns.getString("TYPE_NAME");
                    int columnSize = columns.getInt("COLUMN_SIZE");
                    columnMap.put(columnName, String.format("%s ($d)", columnType, columnSize));
                }
            }
        } catch (SQLException e) {
        }

        return columnMap;
    }
}
