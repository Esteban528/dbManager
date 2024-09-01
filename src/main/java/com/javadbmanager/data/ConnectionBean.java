package com.javadbmanager.data;

import java.io.Serializable;

public class ConnectionBean implements Serializable {

  private String host;
  private String port;
  private String username;
  private String password;
  private String DBType = "MySQL";
  private double DBversion = 5;
  private String database;

  public ConnectionBean() {

  }

  public ConnectionBean(String host, String port, String username, String password, String dBType, double dBversion,
      String database) {

    this.host = host;
    this.port = port;
    this.username = username;
    this.password = password;
    this.DBType = dBType;
    this.DBversion = dBversion;
    this.database = database;
  }

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public String getPort() {
    return port;
  }

  public void setPort(String port) {
    this.port = port;
  }

  public void setPort(int port) {
    this.port = Integer.toString(port);
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getDBType() {
    return DBType;
  }

  public void setDBType(String dBType) {
    DBType = dBType;
  }

  public double getDBversion() {
    return DBversion;
  }

  public void setDBversion(double dBversion) {
    DBversion = dBversion;
  }

  public String getDatabase() {
    return database;
  }

  public void setDatabase(String database) {
    this.database = database;
  }

}
