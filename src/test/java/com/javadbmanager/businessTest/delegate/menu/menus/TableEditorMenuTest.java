package com.javadbmanager.businessTest.delegate.menu.menus;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

import java.util.HashMap;
import java.util.Map;

import com.javadbmanager.business.delegate.menu.CommandHandler;
import com.javadbmanager.business.delegate.menu.Menu;
import com.javadbmanager.business.delegate.menu.MenuManagerImpl;
import com.javadbmanager.business.delegate.menu.MenuType;
import com.javadbmanager.business.delegate.menu.menus.TableEditorMenu;
import com.javadbmanager.business.logic.ConnectionBeanBuilder;
import com.javadbmanager.business.logic.TableManagerService;
import com.javadbmanager.business.logic.exceptions.BusinessException;
import com.javadbmanager.presentation.DisplayCLI;

public class TableEditorMenuTest {

  @Mock
  DisplayCLI display;

  @Mock
  MenuManagerImpl menuManager;

  @Mock
  ConnectionBeanBuilder connectionBeanBuilder;

  @Mock
  TableManagerService tableManagerService;

  TableEditorMenu tableEditorMenu;

  Map<Integer, CommandHandler> options;
  MenuType defaultMenuToRedirect = MenuType.MainMenu;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    tableEditorMenu = new TableEditorMenu(display, menuManager, tableManagerService);
    options = tableEditorMenu.getOptions();
  }

  @Test
  void constructorTest() {
    assertInstanceOf(Menu.class, tableEditorMenu);
  }

  @Test
  void testCreateTable() throws BusinessException {
    String tableName = "my_table";
    when(display.scanLine())
        .thenReturn(tableName) // Table name
        .thenReturn("column1") // First column name
        .thenReturn("NOT NULL") // First column constraints
        .thenReturn("another_column") // Another column name if needed
        .thenReturn("PRIMARY KEY") // Constraints for the next column
        .thenReturn("") // Exit condition for columns
        .thenReturn("exit"); // Exit keyword for early termination
    HashMap<String, String> hMap = new HashMap<>();
    hMap.put("column1", "NOT NULL");
    hMap.put("another_column", "PRIMARY KEY");

    options.get(1).execute();
    verify(tableManagerService).setTableName(tableName);
    verify(tableManagerService).create(tableName, hMap);
    verify(display).sendSuccessLog(anyString());
    verify(menuManager).load(defaultMenuToRedirect);
  }

  @Test
  void testCreateTableWithExceptions() throws BusinessException {
    when(display.scanLine())
        .thenReturn("my_table") // Table name
        .thenReturn("exit"); // Exit keyword for early termination

    options.get(1).execute();
    verify(tableManagerService, never()).create(anyString(), anyMap());
    verify(menuManager).load(defaultMenuToRedirect);
    verify(display).sendErrorLog(anyString());
  }

  @Test
  void testCreateColumns() throws BusinessException {
    when(display.scanLine())
        .thenReturn("my_table") // Table name
        .thenReturn("user_id") // Column name
        .thenReturn("VARCHAR (50)") // Data type
        .thenReturn("PRIMARY KEY") // First constraint
        .thenReturn("NOT NULL") // Second constraint
        .thenReturn("UNIQUE") // Third constraint
        .thenReturn("exit"); // Exit keyword (if needed)

    options.get(2).execute();
    verify(tableManagerService).setTableName(eq("my_table"));
    verify(tableManagerService).addColumn(eq("user_id"), eq("VARCHAR (50)"), eq("NOT NULL"), eq("PRIMARY KEY"),
        eq("UNIQUE"));
    verify(menuManager).load(defaultMenuToRedirect);
  }

  @Test
  void testCreateColumnsWithExceptions() throws BusinessException {
    when(display.scanLine())
        .thenReturn("my_table") // Table name
        .thenReturn("") // Empty dataType
        .thenReturn("exit"); // Exit keyword for early termination

    options.get(2).execute();
    verify(tableManagerService, never()).addColumn(anyString(), anyString(), (String[]) any());
    verify(menuManager).load(defaultMenuToRedirect);

    verify(display).sendErrorLog(anyString());
  }

  @Test
  void testRemoveColumn() throws BusinessException {
    when(display.scanLine())
        .thenReturn("my_table") // Table name
        .thenReturn("user_id"); // Column name

    options.get(3).execute();

    verify(tableManagerService).setTableName(eq("my_table"));
    verify(tableManagerService).removeColumn(eq("user_id"));
    verify(menuManager).load(defaultMenuToRedirect);

  }

  @Test
  void testRemoveColumnWithExceptions() throws BusinessException {
    when(display.scanLine())
        .thenReturn("my_table") // Table name
        .thenReturn(""); // Empty dataType

    options.get(3).execute();
    verify(tableManagerService, never()).removeColumn(anyString());
    verify(menuManager).load(defaultMenuToRedirect);

    verify(display).sendErrorLog(anyString());
  }

  @Test
  void testRenameColumn() throws BusinessException {
    when(display.scanLine())
        .thenReturn("my_table") // Table name
        .thenReturn("user_id") // Column name
        .thenReturn("userid"); // New name

    options.get(4).execute();

    verify(tableManagerService).setTableName(eq("my_table"));
    verify(tableManagerService).renameColumn(eq("user_id"), eq("userid"));
    verify(menuManager).load(defaultMenuToRedirect);

  }

  @Test
  void testRenameColumnWithExceptions() throws BusinessException {
    when(display.scanLine())
        .thenReturn("my_table") // Table name
        .thenReturn(""); // Empty dataType

    options.get(4).execute();
    verify(tableManagerService, never()).renameColumn(anyString(), anyString());
    verify(menuManager).load(defaultMenuToRedirect);

    verify(display).sendErrorLog(anyString());
  }

  @Test
  void testEditColumn() throws BusinessException {
    when(display.scanLine())
        .thenReturn("my_table") // Table name
        .thenReturn("user_id") // Column name
        .thenReturn("INT (11)") // DataType
        .thenReturn("PRIMARY KEY")
        .thenReturn("exit"); // New name

    options.get(5).execute();

    verify(tableManagerService).setTableName(eq("my_table"));
    verify(tableManagerService).editColumn(eq("user_id"), eq("INT (11)"), eq("PRIMARY KEY"));
    verify(menuManager).load(defaultMenuToRedirect);

  }

  // @Test
  void testEditColumnWithExceptions() throws BusinessException {
    when(display.scanLine())
        .thenReturn("my_table") // Table name
        .thenReturn("exit"); // Empty dataType

    options.get(5).execute();
    verify(tableManagerService, never()).renameColumn(anyString(), anyString());
    verify(menuManager).load(defaultMenuToRedirect);

    verify(display).sendErrorLog(anyString());
  }

  @Test
  void testShowTableProperties() throws BusinessException {
    String tableName = "my_table";
    Map<String, String> properties = new HashMap<String, String>();

    when(display.scanLine())
        .thenReturn(tableName) // Table name
        .thenReturn("exit");

    when(tableManagerService.getTableProperties()).thenReturn(properties);

    options.get(6).execute();

    verify(tableManagerService).setTableName(tableName);
    verify(tableManagerService).getTableProperties();
    verify(menuManager).load(defaultMenuToRedirect);

  }

  // @Test
  void testShowTablePropertiesWithExceptions() throws BusinessException {
    when(display.scanLine())
        .thenReturn("") // Table name
        .thenReturn("exit"); // Empty dataType

    options.get(6).execute();
    verify(tableManagerService, never()).getTableProperties();
    verify(menuManager).load(defaultMenuToRedirect);

    verify(display).sendErrorLog(anyString());
  }
}
