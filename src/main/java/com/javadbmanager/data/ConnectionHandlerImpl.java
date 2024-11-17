package com.javadbmanager.data;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

import com.javadbmanager.data.utils.DataUtils;

/**
 * This class handles the connection to the database.
 * 
 * @author Your Name
 */
public class ConnectionHandlerImpl implements ConnectionHandler {
  private Connection connection;
  private ConnectionBean connectionBean;
  private DataUtils dataUtils;

  /**
   * Constructor for the ConnectionHandlerImpl class.
   * 
   * @param connectionBean The ConnectionBean object containing the database credentials.
   * @param dataUtils The DataUtils object for handling database operations.
   */
  public ConnectionHandlerImpl(ConnectionBean connectionBean, DataUtils dataUtils) {
    this.connectionBean = connectionBean;
    this.dataUtils = dataUtils;
  }

  /**
   * Returns the ConnectionBean object.
   * 
   * @return The ConnectionBean object.
   */
  public ConnectionBean getConnectionBean() {
    return connectionBean;
  }

  /**
   * Creates a new connection to the database.
   * 
   * @throws SQLException If an error occurs while creating the connection.
   */
  public void createConnection() throws SQLException {
    if (connection != null) {
      connection.close();
    }

    this.connection = getDataSource(connectionBean, dataUtils.makeUrl(connectionBean)).getConnection();
    // this.connection.setAutoCommit(false);
  }

  /**
   * Gets the DataSource object for the database connection.
   * 
   * @param connectionBean The ConnectionBean object.
   * @param url The database URL.
   * @return The DataSource object.
   * @throws SQLException If an error occurs while getting the DataSource.
   */
  public DataSource getDataSource(ConnectionBean connectionBean, String url) throws SQLException {
    BasicDataSource dataSource = new BasicDataSource();
    dataSource.setUrl(url);
    dataSource.setUsername(connectionBean.getUsername());
    dataSource.setPassword(connectionBean.getPassword());
    dataSource.setInitialSize(3);

    return dataSource;
  }

  /**
   * Gets the existing connection.
   * 
   * @return The Connection object.
   * @throws SQLException If an error occurs while getting the connection.
   */
  public Connection getConnection() throws SQLException {
    if (connection == null) {
      createConnection();
    }
    return connection;
  }

  /**
   * Closes the connection to the database.
   * 
   * @throws SQLException If an error occurs while closing the connection.
   */
  @Override
  public void close() throws SQLException {
    this.connection.close();
  }
}
