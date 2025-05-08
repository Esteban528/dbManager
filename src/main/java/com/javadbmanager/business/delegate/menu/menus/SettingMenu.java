package com.javadbmanager.business.delegate.menu.menus;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.javadbmanager.business.delegate.menu.Menu;
import com.javadbmanager.business.delegate.menu.MenuManager;
import com.javadbmanager.business.delegate.menu.MenuType;
import com.javadbmanager.business.logic.ConnectionBeanBuilder;
import com.javadbmanager.business.logic.DataLayerProvider;
import com.javadbmanager.business.logic.EnvManagerService;
import com.javadbmanager.business.logic.utils.ConnectionUtil;
import com.javadbmanager.presentation.Display;
import com.javadbmanager.presentation.exceptions.EmptyValueException;

public class SettingMenu extends Menu {
  private EnvManagerService envManagerService;

  public SettingMenu(
      Display display,
      MenuManager menuManager,
      ConnectionBeanBuilder connectionBeanBuilder,
      ConnectionUtil connectionUtil,
      EnvManagerService envManagerService) {

    super("Database connection's settings", "Configure connection of database", MenuType.Config, display, menuManager);
    loadOptions(connectionBeanBuilder, connectionUtil);
    this.envManagerService = envManagerService;
  }

  private void loadOptions(ConnectionBeanBuilder connectionBeanBuilder, ConnectionUtil connectionUtil) {
    super.addOption(1, "Create connection DataBase", () -> {
      try {
        loadConnection(connectionBeanBuilder, connectionUtil);
        display.scanLine();
      } catch (EmptyValueException e) {
      } finally {
        setBusy(false);
        menuManager.getMenuMap().get(MenuType.MainMenu).update();
        menuManager.load(MenuType.MainMenu);
      }
    });
  }

  @SuppressWarnings("unchecked")
  private void loadConnection(ConnectionBeanBuilder connectionBeanBuilder, ConnectionUtil connectionUtil)
      throws EmptyValueException {

    String host, port, username, password, dbType, database;
    double dbVersion;

    display.sendLog("hostname: Default 'localhost'");
    host = display.scanLine();
    display.sendLog(host);

    display.sendLog("port: Default 3306");
    port = display.scanLine();
    display.sendLog(port);

    display.sendLog("username: Default: root");
    username = display.scanLine();
    display.sendLog(username);

    display.sendLog("password: ");
    password = display.scanLine();

    display.sendLog("Database Type (Default mysql): ");
    dbType = display.scanLine();
    display.sendLog(dbType);

    display.sendLog("Database version (Default 5.0): ");
    dbVersion = display.scanDouble();
    display.sendLog(Double.toString(dbVersion));


    setBusy(true);
    connectionBeanBuilder
        .setHost((host.isBlank() ? "localhost" : host))
        .setPort((port.isBlank() ? "3306" : port))
        .setUsername((username.isBlank() ? "root" : username))
        .setPassword(password)
        .setDBType(dbType)
        .setDBVersion(dbVersion);

    String dbLists = "";

    if (connectionBeanBuilder.getDbType().equalsIgnoreCase("mysql")) {
        Object result = connectionUtil.run(connectionBeanBuilder, (con) -> {
            try {
              DatabaseMetaData metaData = con.getMetaData();
              ResultSet rs = metaData.getCatalogs();
              Set<String> dbNameSet = new HashSet<>();
              while (rs.next()) {
                String dbName = rs.getString("TABLE_CAT");
                dbNameSet.add(dbName);
              }
              return dbNameSet;
          } catch (SQLException e) {
            return null;
          }
        });
      if (result != null) {
        dbLists = ((Set<String>) result).stream().collect(Collectors.joining(", "));
      }
    }

    display.sendLog("Database name " + String.format("( %s ):", dbLists));
    database = display.scanLine();
    display.sendLog(database);
    connectionBeanBuilder.setDatabase(database);

    display.clean();
    display.sendLog("Testing connection...");
    boolean connectionTest = connectionUtil.test(connectionBeanBuilder);
    if (connectionTest) {
      display.sendLog("Connection OK");
      envManagerService.set("ConnectionBean", connectionBeanBuilder.build());
      DataLayerProvider dataLayerProvider = (DataLayerProvider) envManagerService.get("dataLayerProvider");
      dataLayerProvider.initDataLayer();
    } else {
      display.sendLog("Connection Failed");
      display.scan();
    }
  }
}
