package com.javadbmanager.business.delegate.menu.menus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.javadbmanager.business.delegate.menu.Menu;
import com.javadbmanager.business.delegate.menu.MenuManager;
import com.javadbmanager.business.delegate.menu.MenuType;
import com.javadbmanager.business.logic.DataService;
import com.javadbmanager.business.logic.TableManagerService;
import com.javadbmanager.business.logic.exceptions.BusinessException;
import com.javadbmanager.presentation.Display;
import com.javadbmanager.presentation.DisplayUtils;
import com.javadbmanager.presentation.exceptions.EmptyValueException;

public class CrudMenu extends Menu {
  private CrudMenuOptions options;
  private String tableName = null;
  public final static String DEFAULT_TABLE_NAME = "NOT SELECTED";

  public CrudMenu(Display display, MenuManager menuManager, DataService dataService,
      TableManagerService tableManagerService) {

    super("Crud Menu", "Option for data management (TABLE NOT SELECTED)", MenuType.CrudManager, display,
        menuManager);

    this.tableName = DEFAULT_TABLE_NAME;
    options = new CrudMenuOptions(this, display, menuManager, dataService, tableManagerService);
    loadOptions();

  }

  private void loadOptions() {
    options.selectTableOption();
    options.insertOption();
    options.getDataOption();
    options.updateOption();
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

  public void selectTableOption() {
    menu.addOption(1, "Select a table", () -> {
      try {
        selectTable();
      } catch (EmptyValueException e) {
        display.sendErrorLog(e.getMessage());
        sendToDefaultMenu();
      }
    });
  }

  public void insertOption() {
    menu.addOption(2, "Insert data", () -> {
      try {
        selectTable();
        Map<String, String> insertItems = new HashMap<>();
        Set<String> columns = tableManagerService.getTableProperties().keySet();

        for (String col : columns) {
          display.sendLog(String.format("Insert value to \033[1m %s \033[0m Type an space for empty value", col));
          String value = display.scanLine();
          insertItems.put(col, value);
        }

        insert(insertItems);
      } catch (BusinessException | EmptyValueException e) {
        display.sendErrorLog(e.getMessage());
      } finally {
        sendToDefaultMenu();
      }
    });
  }

  public void getDataOption() {
    menu.addOption(3, "Select data", () -> {
      try {
        selectTable();
        display.sendLog("Write the filter in the form (id=2)");
        String filter = display.scanLine();
        Map<String, String> filterMap = DisplayUtils.parseMessageToMap(filter);
        showData(filterMap);
      } catch (EmptyValueException | BusinessException e) {
        display.sendErrorLog(e.getMessage());
      } finally {
        sendToDefaultMenu();
      }
    });
  }

  public void updateOption() {
    menu.addOption(4, "Update data", () -> {
      try {
        selectTable();

        Map<String, String> updateItems = new HashMap<>();
        Set<String> columns = tableManagerService.getTableProperties().keySet();

        display.sendLog("Update data: ");
        for (String column : columns) {
          String message = String.format("Update %s", column);
          display.sendLog(message);
          String value = display.scanLine();

          if (!value.isBlank() && !value.isEmpty()) {
            updateItems.put(column, value);
          }
        }

        display.sendLog("Write the Where in the form (id=5)");
        String filter = display.scanLine();
        Map<String, String> filterMap = DisplayUtils.parseMessageToMap(filter);
        updateData(updateItems, filterMap);

      } catch (EmptyValueException | BusinessException e) {
        display.sendErrorLog(e.getMessage());
      } finally {
        sendToDefaultMenu();
      }
    });
  }

  private void updateData(Map<String, String> items, Map<String, String> wheres) throws BusinessException {
    dataService.update(items, wheres);
  }

  public void showData(Map<String, String> filterMap) throws BusinessException {
    List<Map<String, String>> items = (filterMap.isEmpty()) ? dataService.get() : dataService.get(filterMap);

    items.forEach(item -> {
      item.keySet().forEach(key -> {
        String message = String.format("%s = %s", key, item.get(key));
        display.addLog(message);
      });
    });
    display.show();
  }

  public void selectTable() throws EmptyValueException {
    if (menu.getTableName().equals(CrudMenu.DEFAULT_TABLE_NAME)) {
      display.sendLog("Enter the table name");
      String tableName = display.scanLine();
      menu.setTableName(tableName);
      tableManagerService.setTableName(tableName);
      dataService.setTableName(tableName);
    }
  }

  void insert(Map<String, String> items) throws EmptyValueException {
    if (items.isEmpty()) {
      throw new EmptyValueException("The elements to be inserted are empty");
    }
    try {
      dataService.insert(items);
    } catch (BusinessException e) {
      display.sendErrorLog(e.getMessage());
      sendToDefaultMenu();
    }
  }

  void sendToDefaultMenu() {
    menuManager.load(MenuType.CrudManager);
  }
}
