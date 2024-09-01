package com.javadbmanager.business.delegate.menu.menus;

import com.javadbmanager.business.delegate.menu.Menu;
import com.javadbmanager.business.delegate.menu.MenuManager;
import com.javadbmanager.business.delegate.menu.MenuType;
import com.javadbmanager.business.logic.ConnectionBeanBuilder;
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
    super.addOption(1, "Connect to DataBase", () -> {
      try {
        String host, port, username, password, dbType, database;
        double dbVersion;

        display.sendLog("hostname: ");
        host = display.scanLine();

        display.sendLog("port: ");
        port = display.scanLine();

        display.sendLog("username: ");
        username = display.scanLine();

        display.sendLog("password: ");
        password = display.scanLine();

        display.sendLog("Database Type (Default MySql): ");
        dbType = display.scanLine();

        display.sendLog("Database version (Default 5.0): ");
        dbVersion = display.scanDouble();

        display.sendLog("Database name: ");
        database = display.scanLine();

        setBusy(true);
        connectionBeanBuilder
            .setHost(host)
            .setPort(port)
            .setUsername(username)
            .setPassword(password)
            .setDBType(dbType)
            .setDBVersion(dbVersion)
            .setDatabase(database);

        display.clean();
        display.sendLog("Testing connection...");
        boolean connectionTest = connectionUtil.test(connectionBeanBuilder);
        if (connectionTest) {
          envManagerService.set("ConnectionBean", connectionBeanBuilder.build());
        }
      } catch (EmptyValueException e) {
      } finally {
        setBusy(false);
        menuManager.load(MenuType.MainMenu);
      }
    });
  }
}
