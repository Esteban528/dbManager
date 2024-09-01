package com.javadbmanager.data;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

import com.javadbmanager.data.utils.DataUtils;

public class ConnectionHandlerImpl implements ConnectionHandler {
  private Connection connection;
  private ConnectionBean connectionBean;
  private DataUtils dataUtils;

  public ConnectionHandlerImpl(ConnectionBean connectionBean, DataUtils dataUtils) {
    this.connectionBean = connectionBean;
    this.dataUtils = dataUtils;
  }

  public ConnectionBean getConnectionBean() {
    return connectionBean;
  }

  public void createConnection() throws SQLException {
    if (connection != null) {
      connection.close();
    }

    this.connection = getDataSource(connectionBean, dataUtils.makeUrl(connectionBean)).getConnection();
    this.connection.setAutoCommit(false);
  }

  public DataSource getDataSource(ConnectionBean connectionBean, String url) throws SQLException {
    BasicDataSource dataSource = new BasicDataSource();
    dataSource.setUrl(url);
    dataSource.setUsername(connectionBean.getUsername());
    dataSource.setPassword(connectionBean.getPassword());
    dataSource.setInitialSize(3);

    return dataSource;
  }

  public Connection getConnection() throws SQLException {
    if (connection == null) {
      createConnection();
    }
    return connection;
  }

  @Override
  public void close() throws SQLException {
    this.connection.close();
  }

}
