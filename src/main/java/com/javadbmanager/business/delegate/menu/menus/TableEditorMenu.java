package com.javadbmanager.business.delegate.menu.menus;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.javadbmanager.business.delegate.menu.Menu;
import com.javadbmanager.business.delegate.menu.MenuManager;
import com.javadbmanager.business.delegate.menu.MenuType;
import com.javadbmanager.business.logic.TableManagerService;
import com.javadbmanager.business.logic.exceptions.BusinessException;
import com.javadbmanager.presentation.Display;
import com.javadbmanager.presentation.exceptions.EmptyValueException;

public class TableEditorMenu extends Menu {
  private TableEditorOptions options;

  public TableEditorMenu(Display display, MenuManager menuManager, TableManagerService tableManagerService) {
    super("Table parameter's editor menu", "Option list for tables", MenuType.TableManager, display, menuManager);

    options = new TableEditorOptions(this, display, menuManager, tableManagerService);
    loadOptions();
  }

  private void loadOptions() {
    options.createTableOption();
    options.createColumnOption();
    options.removeColumnOption();
    options.renameColumnOption();
    options.editColumnOption();
    options.showTablePropertiesOption();
  }

}

class TableEditorOptions {
  Display display;
  MenuManager menuManager;
  TableManagerService tableManagerService;
  Menu menu;

  public TableEditorOptions(TableEditorMenu menu, Display display, MenuManager menuManager,
      TableManagerService tableManagerService) {
    this.menu = menu;
    this.display = display;
    this.menuManager = menuManager;
    this.tableManagerService = tableManagerService;
  }

  public void createTableOption() {
    menu.addOption(1, "Create table", () -> {
      boolean typeColumn = true;
      String tableName;
      Map<String, String> columnMap = new HashMap<>();

      try {
        display.sendLog("Table name: ");
        tableName = display.scanLine();

        display.sendLog("Now you have to create the columns, Leave the field blank to stop aggregation");
        while (typeColumn) {
          String name, constraints;
          display.sendLog("Column name: ");
          name = display.scanLine();

          display.sendLog("Enter the constraints separated by spaces: ");
          constraints = display.scanLine();

          name.replaceAll("\\s", "");
          if (name.toLowerCase().contains("exit")
              || constraints.toLowerCase().contains("exit")
              || name.isEmpty()) {
            typeColumn = false;
            break;
          }

          columnMap.put(name, constraints);
        }
        createTable(tableName, columnMap);

      } catch (EmptyValueException e) {
        display.sendErrorLog(e.getMessage());
      } finally {
        loadDefaultMenu();
      }
    });
  }

  public void createColumnOption() {
    menu.addOption(2, "Create column", () -> {
      boolean nextConstraint = true;
      String tableName, columnName, dataType;
      TreeSet<String> constraintSet = new TreeSet<>();
      try {
        display.sendLog("Table name: ");
        tableName = display.scanLine();

        display.sendLog("Column name: [ Example: \"users\" ] ");
        columnName = display.scanLine();

        display.sendLog("Data type: [ Example: \"VARCHAR (50)\" ] ");
        dataType = display.scanLine();

        display.sendLog("Now you have to create the constraints, Leave the field blank to stop aggregation.");
        display.sendLog("Type 'write' to stop aggregation.");

        while (nextConstraint) {
          String constraint;
          display.sendLog("Enter the constraint name: [ Example: PRIMARY KEY ] ");
          constraint = display.scanLine();

          constraint.trim();
          if (constraint.toLowerCase().contains("exit")) {
            nextConstraint = false;
            break;
          }
          constraintSet.add(constraint);
        }

        createColumn(tableName, columnName, dataType, constraintSet);
      } catch (EmptyValueException e) {
        display.sendErrorLog(e.getMessage());
      } finally {
        loadDefaultMenu();
      }
    });
  }

  public void removeColumnOption() {
    menu.addOption(3, "Remove column", () -> {
      String tableName, columnName;
      try {
        display.sendLog("Table name: ");
        tableName = display.scanLine();

        display.sendLog("Column name to remove: ");
        columnName = display.scanLine();
        removeColumn(tableName, columnName);
      } catch (EmptyValueException e) {
        display.sendErrorLog(e.getMessage());
      } finally {
        loadDefaultMenu();
      }
    });
  }

  public void renameColumnOption() {
    menu.addOption(4, "Rename a column", () -> {
      String tableName, columnName, newName;
      try {
        display.sendLog("Table name: ");
        tableName = display.scanLine();

        display.sendLog("Column name to rename: ");
        columnName = display.scanLine();

        display.sendLog("New column name: ");
        newName = display.scanLine();

        renameColumn(tableName, columnName, newName);
      } catch (EmptyValueException e) {
        display.sendErrorLog(e.getMessage());
      } finally {
        loadDefaultMenu();
      }
    });
  }

  public void editColumnOption() {
    menu.addOption(5, "Modify a column", () -> {
      boolean nextConstraint = true;
      String tableName, columnName, dataType;
      Set<String> constraintSet = new LinkedHashSet<>();
      try {
        display.sendLog("Table name: ");
        tableName = display.scanLine();

        display.sendLog("Column name: [ Example: \"users\" ] ");
        columnName = display.scanLine();

        display.sendLog("Data type: [ Example: \"VARCHAR (50)\" ] ");
        dataType = display.scanLine();
        constraintSet.add(dataType);

        display.sendLog("Now you have to create the constraints.");
        display.sendLog("Type 'exit' to stop aggregation.");

        while (nextConstraint) {
          String constraint;
          display.sendLog("Enter the constraint name: [ Example: PRIMARY KEY ] ");
          constraint = display.scanLine();

          constraint.trim();
          if (constraint.toLowerCase().contains("exit")) {
            nextConstraint = false;
            break;
          }
          constraintSet.add(constraint);
        }

        modifyColumn(tableName, columnName, constraintSet);
      } catch (EmptyValueException e) {
        display.sendErrorLog(e.getMessage());
      } finally {
        loadDefaultMenu();
      }
    });
  }

  public void showTablePropertiesOption() {
    menu.addOption(6, "Show table properties and columns", () -> {
      try {
        String tableName;
        display.sendLog("Table name: ");
        tableName = display.scanLine();

        showTableProperties(tableName);
      } catch (EmptyValueException e) {
        loadDefaultMenu();
      }
    });
  }

  private void loadDefaultMenu() {
    menuManager.load(MenuType.MainMenu);
  }

  private void createTable(String tableName, Map<String, String> columnMap) throws EmptyValueException {
    if (!tableName.isEmpty() && !columnMap.isEmpty()) {
      try {
        tableManagerService.setTableName(tableName);
        tableManagerService.create(tableName, columnMap);

        String message = String.format("Table '%s' created successfull", tableName);
        display.sendSuccessLog(message);
      } catch (BusinessException e) {
        display.sendErrorLog(e.getMessage());
      }
    } else
      throw new EmptyValueException("Table name is empty");
  }

  private void createColumn(String tableName, String columnName, String dataType, TreeSet<String> constraintSet)
      throws EmptyValueException {
    if (!tableName.isEmpty() && !columnName.isEmpty() && !dataType.isEmpty()) {
      try {
        String[] constraints = new String[constraintSet.size()];
        constraints = constraintSet.toArray(constraints);
        tableManagerService.setTableName(tableName);
        tableManagerService.addColumn(columnName, dataType, constraints);

        String message = String.format("Column '%s' created successfull in %s", columnName, tableName);
        display.sendSuccessLog(message);
      } catch (BusinessException e) {
        display.sendErrorLog(e.getMessage());
      }
    } else
      throw new EmptyValueException(tableName.isEmpty() ? "Table name is empty"
          : columnName.isEmpty() ? "Column name is empty" : dataType.isEmpty() ? "Datatype is empty" : "");
  }

  private void removeColumn(String tableName, String columnName) throws EmptyValueException {
    if (!tableName.isEmpty() && !columnName.isEmpty()) {
      try {
        tableManagerService.setTableName(tableName);
        tableManagerService.removeColumn(columnName);

        String message = String.format("Column '%s' removed successfull in %s", columnName, tableName);
        display.sendSuccessLog(message);
      } catch (BusinessException e) {
        display.sendErrorLog(e.getMessage());
      }
    } else
      throw new EmptyValueException(tableName.isEmpty() ? "Table name is empty"
          : columnName.isEmpty() ? "Column name is empty" : "");
  }

  private void renameColumn(String tableName, String columnName, String newName) throws EmptyValueException {
    if (!tableName.isEmpty() && !columnName.isEmpty() && !newName.isEmpty()) {
      try {
        tableManagerService.setTableName(tableName);
        tableManagerService.renameColumn(columnName, newName);

        String message = String.format("Column '%s' renamed to '%s' successfull in %s", columnName, newName, tableName);
        display.sendSuccessLog(message);
      } catch (BusinessException e) {
        display.sendErrorLog(e.getMessage());
      }
    } else
      throw new EmptyValueException(tableName.isEmpty() ? "Table name is emp ty"
          : columnName.isEmpty() ? "Column name is empty" : newName.isEmpty() ? "New name is empty" : "");
  }

  private void modifyColumn(String tableName, String columnName, Set<String> constraintSet)
      throws EmptyValueException {
    if (!tableName.isEmpty() && !columnName.isEmpty() && !constraintSet.isEmpty()) {
      try {
        String[] constraints = new String[constraintSet.size()];
        constraints = constraintSet.toArray(constraints);

        tableManagerService.setTableName(tableName);
        tableManagerService.editColumn(columnName, constraints);

        String message = String.format("Column '%s' modify successfull in %s", columnName, tableName);
        display.sendSuccessLog(message);
      } catch (BusinessException e) {
        display.sendErrorLog(e.getMessage());
      }
    } else
      throw new EmptyValueException(tableName.isEmpty() ? "Table name is emp ty"
          : columnName.isEmpty() ? "Column name is empty"
              : constraintSet.isEmpty()
                  ? "DataType cannot be empty"
                  : "");
  }

  private void showTableProperties(String tableName)
      throws EmptyValueException {
    if (tableName.isEmpty()) {
      throw new EmptyValueException("Table name is empty");
    }
    try {
      tableManagerService.setTableName(tableName);
      Map<String, String> properties = tableManagerService.getTableProperties();
      Iterator<String> iterator = properties.keySet().iterator();

      display.addLog("Table properties: (type exit to return to main menu)");
      display.addLog("\n");
      while (iterator.hasNext()) {
        String key = iterator.next();
        String value = properties.get(key);
        display.addLog(String.format("- %s -> %s", key, value));
      }
      display.show();

      while (true) {
        String option = display.scanLine();

        if (option.toLowerCase().equals("exit")) {
          loadDefaultMenu();
          break;
        }
      }

    } catch (BusinessException e) {
      display.sendErrorLog(e.getMessage());
    }
  }
}
