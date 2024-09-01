package com.javadbmanager.businessTest.delegate.menu.menus;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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

  CrudMenu crudMenu;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    crudMenu = new CrudMenu(display, menuManager, dataService, tableManagerService);
  }

}
