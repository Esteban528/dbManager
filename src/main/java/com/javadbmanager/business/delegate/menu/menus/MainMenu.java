package com.javadbmanager.business.delegate.menu.menus;

import com.javadbmanager.business.delegate.menu.Menu;
import com.javadbmanager.business.delegate.menu.MenuManager;
import com.javadbmanager.business.delegate.menu.MenuType;
import com.javadbmanager.business.logic.ConnectionBeanBuilder;
import com.javadbmanager.business.logic.EnvManagerService;
import com.javadbmanager.presentation.Display;

public class MainMenu extends Menu {

  public MainMenu(
      Display display,
      MenuManager menuManager,
      ConnectionBeanBuilder connectionBeanBuilder,
      EnvManagerService envManagerService) {

    super("Main menu", "List of menus", MenuType.MainMenu, display, menuManager);

    boolean existsAConnection = (envManagerService.get("ConnectionBean") != null);

    super.addOption(1,
        (existsAConnection ? "Change Connection to Database" : "Connect to DataBase"), () -> {
          menuManager.load(MenuType.Config);
        });

    if (existsAConnection) {
      super.addOption(2, "Table editor", () -> {
        menuManager.load(MenuType.TableManager);
      });
    }
  }
}
