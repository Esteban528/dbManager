package com.javadbmanager.business.logic;

import com.javadbmanager.data.AnyRepository;
import com.javadbmanager.data.ConnectionHandler;
import com.javadbmanager.data.DataAccessObject;
import com.javadbmanager.data.TableHandler;
import com.javadbmanager.data.utils.DataUtils;
import com.javadbmanager.data.utils.QueryBuilder;

public interface DataLayerProvider {
  DataUtils getDataUtils();

  QueryBuilder getQueryBuilder();

  DataAccessObject getDataAccessObject();

  AnyRepository getAnyRepository();

  TableHandler getTableHandler();

  ConnectionHandler getConnectionHandler();

}
