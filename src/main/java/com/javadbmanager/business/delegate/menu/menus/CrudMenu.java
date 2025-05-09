package com.javadbmanager.business.delegate.menu.menus;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.javadbmanager.business.delegate.menu.Menu;
import com.javadbmanager.business.delegate.menu.MenuManager;
import com.javadbmanager.business.delegate.menu.MenuType;
import com.javadbmanager.business.logic.DataService;
import com.javadbmanager.business.logic.EnvManagerService;
import com.javadbmanager.business.logic.TableManagerService;
import com.javadbmanager.business.logic.exceptions.BusinessException;
import com.javadbmanager.data.ConnectionBean;
import com.javadbmanager.presentation.Display;
import com.javadbmanager.presentation.DisplayUtils;
import com.javadbmanager.presentation.exceptions.EmptyValueException;

public class CrudMenu extends Menu {
  private CrudMenuOptions options;
  private String tableName = null;
  private final EnvManagerService envManagerService;
  public final static String DEFAULT_TABLE_NAME = "NOT SELECTED";

  public CrudMenu(Display display, MenuManager menuManager, DataService dataService,
      TableManagerService tableManagerService, EnvManagerService envManagerService) {

    super("Crud Menu", "Option for data management (TABLE NOT SELECTED)", MenuType.CrudManager, display,
        menuManager);

    this.tableName = DEFAULT_TABLE_NAME;
    this.envManagerService = envManagerService;

    options = new CrudMenuOptions(this, display, menuManager, dataService, tableManagerService);
    loadOptions();

  }

  private void loadOptions() {
    options.selectTableOption();
  }

  public String getTableName() {
    return tableName;
  }

  public void setTableName(String tableName) {
    this.tableName = tableName;
    super.setTitle(String.format("Option for data management  ( %s )", tableName));
  }

  public EnvManagerService getEnvManagerService() {
    return this.envManagerService;
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
      } finally {
        sendToDefaultMenu();
      }
    });
  }

  public void insertOption() {
    menu.addOption(2, "Insert data", () -> {
      try {
        Map<String, String> insertItems = new HashMap<>();
        Map<String, String> columns = tableManagerService.getTableColumns();
        display.sendLog((columns.isEmpty() ? "Empty" : "Good"));

        columns.forEach((col, data) -> {
          display.sendLog(String.format("Insert value to \033[1m %s \033[0m Type an space for empty value", col));

          // display.sendLog(String.format("%s - - - > %s", col, data));
          String value = display.scanLine();
          if (!value.isEmpty() && !value.isBlank())
            insertItems.put(col, value);
        });

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
        display.sendLog("Write the filter in the form (id=2)");
        String filter = display.scanLine();
        Map<String, String> filterMap = DisplayUtils.parseMessageToMap(filter);
        showData(filterMap);
      } catch (BusinessException e) {
        display.sendErrorLog(e.getMessage());
      } finally {
        sendToDefaultMenu();
      }
    });
  }

  public void updateOption() {
    menu.addOption(4, "Update data", () -> {
      try {
        Map<String, String> updateItems = new HashMap<>();
        Set<String> columns = tableManagerService.getTableColumns().keySet();

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

      } catch (BusinessException e) {
        display.sendErrorLog(e.getMessage());
      } finally {
        sendToDefaultMenu();
      }
    });
  }

  public void deleteOption() {
    menu.addOption(5, "Delete data", () -> {
      try {

        display.sendLog("Delete data: ");
        display.sendLog("Write the where in the form (id=5)");
        String filter = display.scanLine();
        Map<String, String> filterMap = DisplayUtils.parseMessageToMap(filter);
        delete(filterMap);
      } catch (BusinessException e) {
        display.sendErrorLog(e.getMessage());
      } finally {
        sendToDefaultMenu();
      }
    });
  }

  private void delete(Map<String, String> where) throws BusinessException {
    dataService.delete(where);
  }

  private void updateData(Map<String, String> items, Map<String, String> wheres) throws BusinessException {
    dataService.update(items, wheres);
  }

  public void showData(Map<String, String> filterMap) throws BusinessException {
    List<Map<String, String>> items = (filterMap.isEmpty()) ? dataService.get() : dataService.get(filterMap);

    items.forEach(item -> {
      display.addLog("- - - - - - - - - - - - - - - - - - - - - - -");
      item.keySet().forEach(key -> {
        String message = String.format("%s = %s", key, item.get(key));
        display.addLog(message);
      });
    });
    display.show();
  }

  @SuppressWarnings("unchecked")
  public void selectTable() throws EmptyValueException {

    ConnectionBean connectionBean = (ConnectionBean) menu.getEnvManagerService().get("ConnectionBean");
    Object tableSetObject = dataService.execute((conn) -> { 
      try {
        DatabaseMetaData md = conn.getMetaData();

        ResultSet rs = md.getTables(connectionBean.getDatabase(), null, "%", new String[] {"TABLE"});
        Set<String> tableSet = new HashSet<>();
        while (rs.next()) {
          tableSet.add(rs.getString(3));
        }
        return tableSet;
      } catch(SQLException e) {return null;}
    });

    display.sendLog("Choose a table name from the following");
    if (tableSetObject != null) {
        display.sendLog(((Set<String>) tableSetObject).stream().collect(Collectors.joining(", ")));;
    }

    display.sendLog("Enter the table name: ");

    String tableName = display.scanLine();
    display.sendSuccessLog(tableName);
    menu.setTableName(tableName);
    tableManagerService.setTableName(tableName);
    dataService.setTableName(tableName);

    insertOption();
    getDataOption();
    updateOption();
    deleteOption();
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
    display.scanLine();
    menuManager.load(MenuType.CrudManager);
  }
}
