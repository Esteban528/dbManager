package com.javadbmanager.businessTest.delegate.menu.menus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.javadbmanager.business.delegate.menu.Menu;
import com.javadbmanager.business.delegate.menu.MenuManagerImpl;
import com.javadbmanager.business.delegate.menu.menus.CrudMenu;
import com.javadbmanager.business.logic.DataService;
import com.javadbmanager.business.logic.TableManagerService;
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

  @InjectMocks
  CrudMenu crudMenu;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    // crudMenu = new CrudMenu(display, menuManager, dataService,
    // tableManagerService);
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

    crudMenu.executeOption(1);

    assertEquals(tableName, crudMenu.getTableName());
    verify(tableManagerService).setTableName(tableName);
    verify(dataService).setTableName(tableName);
  }
}
