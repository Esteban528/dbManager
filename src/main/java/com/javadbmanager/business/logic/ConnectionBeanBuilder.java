package com.javadbmanager.business.logic;

import com.javadbmanager.data.ConnectionBean;

public class ConnectionBeanBuilder {

  private String host = "localhost";
  private String port = "3306";
  private String username = "root";
  private String password = "";
  private String dbType = "mysql";
  private double dbVersion = 8.1;
  private String database = "users";

  public ConnectionBeanBuilder setHost(String host) {
    this.host = host;
    return this;
  }

  public ConnectionBeanBuilder setPort(String port) {
    this.port = port;
    return this;
  }

  public ConnectionBeanBuilder setUsername(String username) {
    this.username = username;
    return this;
  }

  public ConnectionBeanBuilder setPassword(String password) {
    this.password = password;
    return this;
  }

  public ConnectionBeanBuilder setDBType(String dbType) {
    this.dbType = dbType;
    return this;
  }

  public ConnectionBeanBuilder setDBVersion(double dbVersion) {
    this.dbVersion = dbVersion;
    return this;
  }

  public ConnectionBeanBuilder setDatabase(String database) {
    this.database = database;
    return this;
  }

  public ConnectionBean build() {
    return new ConnectionBean(host, port, username, password, dbType, dbVersion, database);
  }
}
