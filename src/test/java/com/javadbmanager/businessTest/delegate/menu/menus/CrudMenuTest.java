package com.javadbmanager.businessTest.delegate.menu.menus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.javadbmanager.business.delegate.menu.Menu;
import com.javadbmanager.business.delegate.menu.MenuManagerImpl;
import com.javadbmanager.business.delegate.menu.menus.CrudMenu;
import com.javadbmanager.business.logic.DataService;
import com.javadbmanager.business.logic.EnvManagerService;
import com.javadbmanager.business.logic.TableManagerService;
import com.javadbmanager.business.logic.exceptions.BusinessException;
import com.javadbmanager.data.ConnectionBean;
import com.javadbmanager.presentation.DisplayCLI;

public class CrudMenuTest {

  @Mock
  DisplayCLI display;

  @Mock
  MenuManagerImpl menuManager;

  @Mock
  DataService dataService;

  @Mock
  TableManagerService tableManagerService;

  @Mock
  EnvManagerService envManagerService;

  @InjectMocks
  CrudMenu crudMenu;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void constructorTest() {
    assertInstanceOf(Menu.class, crudMenu);
  }

  @Test
  void selectTableTest() {
    String tableName = "users";

    when(display.scanLine())
        .thenReturn(tableName);
    when(envManagerService.get("ConnectionBean")).thenReturn(new ConnectionBean());

    crudMenu.executeOption(1);

    assertEquals(tableName, crudMenu.getTableName());
    verify(tableManagerService).setTableName(tableName);
    verify(dataService).setTableName(tableName);
  }

  @Test
  void insertTest() throws BusinessException {
    String tableName = "users";
    String column = "name";
    String value = "estebandev";

    // Table
    when(display.scanLine())
        .thenReturn(tableName);

    // Column
    when(tableManagerService.getTableColumns()).thenReturn(Map.of(column, "test"));

    // Value
    when(display.scanLine())
        .thenReturn(value);

    crudMenu.executeOption(1);
    crudMenu.executeOption(2);
    verify(dataService).insert(Map.of(column, value));
  }

  @Test
  void getDataTest() throws BusinessException {
    String tableName = "users";
    String column = "name";
    String value = "estebandev";
    Map<String, String> map = Map.of("id", "5", column, value);

    // Filter
    when(display.scanLine())
        .thenReturn("");

    // Result
    when(dataService.get()).thenReturn(List.of(map, map));

    crudMenu.executeOption(1);
    crudMenu.executeOption(3);
    verify(dataService).get();
  }

  @Test
  void getDataTestWithFilter() throws BusinessException {
    String tableName = "users";
    String column = "name";
    String value = "estebandev";
    Map<String, String> map = Map.of("id", "5", column, value);
    var filter = Map.of("id", "5");

    // Filter
    when(display.scanLine())
        .thenReturn("id=5");

    // Result
    when(dataService.get(filter)).thenReturn(List.of(map, map));

    crudMenu.executeOption(1);
    crudMenu.executeOption(3);
    verify(dataService).get(filter);
  }

  @Test
  void updateTest() throws BusinessException {

    crudMenu.executeOption(1);
    String column = "name";
    String value = "Carlos";
    Map<String, String> map = Map.of(column, value);
    var filter = Map.of("id", "6");

    // Column
    when(tableManagerService.getTableColumns()).thenReturn(Map.of(column, "6"));

    when(display.scanLine())
        .thenReturn(value)
        .thenReturn("id=6");

    crudMenu.executeOption(4);
    verify(dataService).update(map, filter);
  }

  @Test
  void deleteTest() throws BusinessException {
    var filter = Map.of("id", "6");

    when(display.scanLine())
        .thenReturn("id=6");

    crudMenu.executeOption(1);
    crudMenu.executeOption(5);
    verify(dataService).delete(filter);
  }
}
