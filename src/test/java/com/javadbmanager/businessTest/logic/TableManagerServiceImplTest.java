package com.javadbmanager.businessTest.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.javadbmanager.business.logic.DataLayerProvider;
import com.javadbmanager.business.logic.TableManagerServiceImpl;
import com.javadbmanager.business.logic.exceptions.BusinessException;
import com.javadbmanager.data.TableHandler;

public class TableManagerServiceImplTest {

  private TableManagerServiceImpl tableManagerService;
  private TableHandler tableHandler;
  private DataLayerProvider dataLayerProvider;

  @BeforeEach
  void setUp() {
    tableHandler = mock(TableHandler.class);
    dataLayerProvider = mock(DataLayerProvider.class);
    when(dataLayerProvider.getTableHandler()).thenReturn(tableHandler);

    tableManagerService = new TableManagerServiceImpl(dataLayerProvider);
  }

  @Test
  public void testCreateTable() throws SQLException, BusinessException {
    String tableName = "testTable";
    Map<String, String> columns = new HashMap<>();
    columns.put("id", "INT");
    columns.put("name", "VARCHAR(255)");

    doNothing().when(tableHandler).createTable(tableName, columns);

    tableManagerService.create(tableName, columns);

    verify(tableHandler, times(1)).createTable(tableName, columns);
    assertEquals(tableName, tableManagerService.getTableName());
  }

  @Test
  public void testAddColumn() throws SQLException, BusinessException {
    String tableName = "testTable";
    String columnName = "newColumn";
    String dataType = "VARCHAR(255)";

    tableManagerService.setTableName(tableName);
    doNothing().when(tableHandler).createColumn(tableName, columnName, dataType);

    tableManagerService.addColumn(columnName, dataType);

    verify(tableHandler, times(1)).createColumn(tableName, columnName, dataType);
  }

  @Test
  public void testRemoveColumn() throws SQLException, BusinessException {
    String tableName = "testTable";
    String columnName = "name";

    tableManagerService.setTableName(tableName);
    doNothing().when(tableHandler).removeColumn(tableName, columnName);

    tableManagerService.removeColumn(columnName);

    verify(tableHandler, times(1)).removeColumn(tableName, columnName);
  }

  @Test
  public void testRenameColumn() throws SQLException, BusinessException {
    String tableName = "testTable";
    String columnName = "name";
    String newName = "newName";

    tableManagerService.setTableName(tableName);
    doNothing().when(tableHandler).renameColumn(tableName, columnName, newName);

    tableManagerService.renameColumn(columnName, newName);

    verify(tableHandler, times(1)).renameColumn(tableName, columnName, newName);
  }

  @Test
  public void testEditColumn() throws SQLException, BusinessException {
    String tableName = "testTable";
    String columnName = "name";
    String[] constraints = { "NOT NULL" };

    tableManagerService.setTableName(tableName);
    doNothing().when(tableHandler).editColumn(tableName, columnName, constraints);

    tableManagerService.editColumn(columnName, constraints);

    verify(tableHandler, times(1)).editColumn(tableName, columnName, constraints);
  }

  @Test
  public void testGetTableProperties() throws SQLException, BusinessException {
    String tableName = "testTable";
    Map<String, String> properties = new HashMap<>();
    properties.put("id", "INT");
    properties.put("name", "VARCHAR(255)");

    tableManagerService.setTableName(tableName);
    when(tableHandler.getTableProperties(tableName)).thenReturn(properties);

    Map<String, String> result = tableManagerService.getTableProperties();

    verify(tableHandler, times(1)).getTableProperties(tableName);
    assertEquals(properties, result);
  }

  @Test
  public void testCreateTable_ThrowsBusinessException() throws SQLException {
    String tableName = "testTable";
    Map<String, String> columns = new HashMap<>();
    columns.put("id", "INT");

    doThrow(new SQLException("SQL Error")).when(tableHandler).createTable(tableName, columns);

    BusinessException exception = assertThrows(BusinessException.class, () -> {
      tableManagerService.create(tableName, columns);
    });

    assertEquals("SQL Error", exception.getMessage());
  }

  @Test
  public void testAddColumn_ThrowsBusinessException() throws SQLException {
    String tableName = "testTable";
    String columnName = "newColumn";
    String dataType = "VARCHAR(255)";

    tableManagerService.setTableName(tableName);
    doThrow(new SQLException("SQL Error")).when(tableHandler).createColumn(tableName, columnName, dataType);

    BusinessException exception = assertThrows(BusinessException.class, () -> {
      tableManagerService.addColumn(columnName, dataType);
    });

    assertEquals("SQL Error", exception.getMessage());
  }

  // Puedes agregar pruebas adicionales para otros métodos que lanzan
  // BusinessException

}
