package com.javadbmanager.business.logic;

import java.sql.SQLException;

import com.javadbmanager.data.AnyRepository;
import com.javadbmanager.data.AnyRepositoryImpl;
import com.javadbmanager.data.ConnectionBean;
import com.javadbmanager.data.ConnectionHandler;
import com.javadbmanager.data.ConnectionHandlerImpl;
import com.javadbmanager.data.DataAccessObject;
import com.javadbmanager.data.DataAccessObjectImpl;
import com.javadbmanager.data.TableHandler;
import com.javadbmanager.data.TableHandlerImpl;
import com.javadbmanager.data.utils.DataUtils;
import com.javadbmanager.data.utils.DataUtilsImpl;
import com.javadbmanager.data.utils.QueryBuilder;
import com.javadbmanager.data.utils.QueryBuilderImpl;

public class DefaultDataLayerProvider implements DataLayerProvider {
  private DataUtils dataUtils;
  private QueryBuilder queryBuilder;
  private DataAccessObject dataAccessObject;
  private ConnectionHandler connectionHandler;
  private AnyRepository anyRepository;
  private TableHandler tableHandler;
  private EnvManagerService envManagerService;

  public DefaultDataLayerProvider(EnvManagerService envManagerService) {
    this.envManagerService = envManagerService;
  }

  public void initDataLayer() {
    try {
      dataUtils = new DataUtilsImpl();
      connectionHandler = new ConnectionHandlerImpl((ConnectionBean) envManagerService.get("ConnectionBean"),
          dataUtils);
      queryBuilder = new QueryBuilderImpl();
      dataAccessObject = new DataAccessObjectImpl(connectionHandler.getConnection());
      tableHandler = new TableHandlerImpl(connectionHandler, queryBuilder, dataUtils);
      anyRepository = new AnyRepositoryImpl(connectionHandler, queryBuilder, dataAccessObject);

      TableManagerService tableManagerService = (TableManagerService) envManagerService.get("tableManagerService");
      tableManagerService.init();

      DataService dataService = (DataService) envManagerService.get("dataService");
      dataService.init();

    } catch (SQLException e) {
      System.out.println(e.getCause());
    }
  }

  @Override
  public DataUtils getDataUtils() {
    return dataUtils;
  }

  @Override
  public QueryBuilder getQueryBuilder() {
    return queryBuilder;
  }

  @Override
  public DataAccessObject getDataAccessObject() {
    return dataAccessObject;
  }

  @Override
  public AnyRepository getAnyRepository() {
    return anyRepository;
  }

  @Override
  public TableHandler getTableHandler() {
    return tableHandler;
  }

  @Override
  public ConnectionHandler getConnectionHandler() {
    return connectionHandler;
  }
}
