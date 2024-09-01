package com.javadbmanager.business.delegate.menu.menus;

import java.util.Map;

import com.javadbmanager.business.delegate.menu.Menu;
import com.javadbmanager.business.delegate.menu.MenuManager;
import com.javadbmanager.business.delegate.menu.MenuType;
import com.javadbmanager.business.logic.DataService;
import com.javadbmanager.business.logic.TableManagerService;
import com.javadbmanager.presentation.Display;
import com.javadbmanager.presentation.exceptions.EmptyValueException;

public class CrudMenu extends Menu {
  private CrudMenuOptions options;
  private String tableName = null;

  public CrudMenu(Display display, MenuManager menuManager, DataService dataService,
      TableManagerService tableManagerService) {

    super("Crud Menu", "Option for data management (TABLE NOT SELECTED)", MenuType.CrudManager, display,
        menuManager);

    this.tableName = "NOT SELECTED";
    options = new CrudMenuOptions(this, display, menuManager, dataService, tableManagerService);
    loadOptions();

  }

  private void loadOptions() {
    options.insertOption();
  }

  public String getTableName() {
    return tableName;
  }

  public void setTableName(String tableName) {
    this.tableName = tableName;
    super.setTitle(String.format("Option for data management  ( %s )", tableName));
  }
}

class CrudMenuOptions {
  Display display;
  MenuManager menuManager;
  DataService dataService;
  TableManagerService tableManagerService;
  CrudMenu menu;

  public CrudMenuOptions(CrudMenu menu, Display display, MenuManager menuManager,
      DataService dataService, TableManagerService tableManagerService) {
    this.menu = menu;
    this.display = display;
    this.menuManager = menuManager;
    this.dataService = dataService;
    this.tableManagerService = tableManagerService;
  }

  public void insertOption() {
    menu.addOption(1, "Insert data", () -> {
      try {
        selectTable();
      } catch (EmptyValueException e) {
      }
    });
  }

  public void selectTable() throws EmptyValueException {
    if (menu.getTableName() == null) {
      display.sendLog("Enter the table name");
      String tableName = display.scanLine();
      menu.setTableName(tableName);
    }
  }

  public void insert(String tableName, Map<String, String> items) {

  }

}
