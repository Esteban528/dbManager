package com.javadbmanager.business.logic;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.javadbmanager.business.logic.exceptions.BusinessException;
import com.javadbmanager.data.AnyRepository;
import com.javadbmanager.data.ConnectionHandler;
import com.javadbmanager.data.exceptions.ColumnNotFoundException;
import com.javadbmanager.data.utils.DataUtils;

public class DataServiceImpl implements DataService {
  private ConnectionHandler connectionHandler;
  private AnyRepository anyRepository;
  private DataUtils dataUtils;
  private final DataLayerProvider dataLayerProvider;
  private String tableName;

  public DataServiceImpl(DataLayerProvider dataLayerProvider) {
    this.dataLayerProvider = dataLayerProvider;

    init();
  }

  @Override
  public void init() {
    this.connectionHandler = dataLayerProvider.getConnectionHandler();
    this.anyRepository = dataLayerProvider.getAnyRepository();
    this.dataUtils = dataLayerProvider.getDataUtils();
  }

  public ConnectionHandler getConnectionHandler() {
    return connectionHandler;
  }

  public AnyRepository getAnyRepository() {
    return anyRepository;
  }

  @Override
  public void setTableName(String tableName) {
    this.tableName = tableName;
    anyRepository.setTableName(tableName, dataUtils);
  }

  @Override
  public String getTableName() {
    return tableName;
  }

  @Override
  public int insert(Map<String, String> items) throws BusinessException {
    int rows = 0;
    try {
      rows = anyRepository.insert(items);
    } catch (ColumnNotFoundException | SQLException e) {
      throw new BusinessException(e.getMessage(), e);
    }
    return rows;
  }

  @Override
  public List<Map<String, String>> get() throws BusinessException {
    List<Map<String, String>> results;
    try {
      results = anyRepository.get();
    } catch (ColumnNotFoundException | SQLException e) {
      throw new BusinessException(e.getMessage(), e);
    }

    return results;
  }

  @Override
  public List<Map<String, String>> get(Map<String, String> where) throws BusinessException {
    List<Map<String, String>> results;
    try {
      results = anyRepository.get(where);
    } catch (ColumnNotFoundException | SQLException e) {
      throw new BusinessException(e.getMessage(), e);
    }

    return results;
  }

  @Override
  public int update(Map<String, String> items, Map<String, String> wheres) throws BusinessException {
    int rows = 0;
    try {
      rows = anyRepository.update(items, wheres);
    } catch (ColumnNotFoundException | SQLException e) {
      throw new BusinessException(e.getMessage(), e);
    }
    return rows;
  }

  @Override
  public int delete(Map<String, String> where) throws BusinessException {
    int rows = 0;
    try {
      rows = anyRepository.delete(where);
    } catch (ColumnNotFoundException | SQLException e) {
      throw new BusinessException(e.getMessage(), e);
    }
    return rows;
  }

  @Override
  public void commit() throws BusinessException {
    try {
      anyRepository.commitChange();
    } catch (ColumnNotFoundException | SQLException e) {
      throw new BusinessException(e.getMessage(), e);
    }

  }

  @Override
  public void commitAndClose() throws BusinessException {
    this.commit();
    try {
      this.connectionHandler.close();
    } catch (SQLException e) {
      throw new BusinessException(e.getMessage(), e);
    }
  }
}
