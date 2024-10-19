package com.javadbmanager.business.delegate.menu.menus;

import com.javadbmanager.business.delegate.menu.Menu;
import com.javadbmanager.business.delegate.menu.MenuManager;
import com.javadbmanager.business.delegate.menu.MenuType;
import com.javadbmanager.business.logic.ConnectionBeanBuilder;
import com.javadbmanager.business.logic.EnvManagerService;
import com.javadbmanager.presentation.Display;

public class MainMenu extends Menu {
  private EnvManagerService envManagerService;

  public MainMenu(
      Display display,
      MenuManager menuManager,
      ConnectionBeanBuilder connectionBeanBuilder,
      EnvManagerService envManagerService) {

    super("Main menu", "List of menus", MenuType.MainMenu, display, menuManager);

    this.envManagerService = envManagerService;
    super.addOption(1,
        "Configure connection to DataBase", () -> {
          menuManager.load(MenuType.Config);
        });
  }

  @Override
  public void update() {
    super.getOptions().clear();
    boolean existsAConnection = (envManagerService.get("ConnectionBean") != null);

    super.addOption(1,
        (existsAConnection ? "Change Connection to Database" : "Configure connection to DataBase"), () -> {
          menuManager.load(MenuType.Config);
        });

    if (existsAConnection) {
      super.addOption(2, getMenu(MenuType.TableManager).getTitle(), () -> {
        menuManager.load(MenuType.TableManager);
      });

      super.addOption(3, getMenu(MenuType.CrudManager).getTitle(), () -> {
        menuManager.load(MenuType.CrudManager);
      });
    }
  }

  private Menu getMenu(MenuType menuType) {
    return menuManager.getMenuMap().get(menuType);
  }
}
