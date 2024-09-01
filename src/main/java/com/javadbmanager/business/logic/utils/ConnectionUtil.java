package com.javadbmanager.business.logic.utils;

import java.sql.Connection;
import java.sql.SQLException;

import com.javadbmanager.business.logic.ConnectionBeanBuilder;
import com.javadbmanager.data.ConnectionBean;
import com.javadbmanager.data.ConnectionHandler;
import com.javadbmanager.data.ConnectionHandlerImpl;
import com.javadbmanager.data.utils.DataUtilsImpl;

public class ConnectionUtil {
  public boolean test(ConnectionBeanBuilder connectionBeanBuilder) {
    ConnectionBean connectionBean = connectionBeanBuilder.build();
    boolean result = false;
    try {
      ConnectionHandler connectionHandler = new ConnectionHandlerImpl(connectionBean, new DataUtilsImpl());
      Connection con = connectionHandler.getConnection();
      result = con.isValid(5);
      con.close();
    } catch (SQLException e) {
      result = false;
    }

    return result;
  }
}
