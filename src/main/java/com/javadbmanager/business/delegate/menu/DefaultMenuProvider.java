package com.javadbmanager.business.delegate.menu;

import java.util.EnumMap;

import com.javadbmanager.business.delegate.menu.menus.CrudMenu;
import com.javadbmanager.business.delegate.menu.menus.MainMenu;
import com.javadbmanager.business.delegate.menu.menus.SettingMenu;
import com.javadbmanager.business.delegate.menu.menus.TableEditorMenu;
import com.javadbmanager.business.logic.ConnectionBeanBuilder;
import com.javadbmanager.business.logic.DataService;
import com.javadbmanager.business.logic.EnvManagerService;
import com.javadbmanager.business.logic.TableManagerService;
import com.javadbmanager.business.logic.utils.ConnectionUtil;
import com.javadbmanager.presentation.Display;

public class DefaultMenuProvider implements MenuProvider {

  // Main Menu
  private EnumMap<MenuType, Menu> menuMap;

  public DefaultMenuProvider(Display display, MenuManager menuManager, ConnectionBeanBuilder connectionBeanBuilder,
      EnvManagerService envManagerService, TableManagerService tableManagerService, DataService dataService) {
    menuMap = new EnumMap<>(MenuType.class);

    Menu mainMenu = new MainMenu(display, menuManager, connectionBeanBuilder, envManagerService);
    Menu settingMenu = new SettingMenu(display, menuManager, connectionBeanBuilder, new ConnectionUtil(),
        envManagerService);
    Menu tableEditorMenu = new TableEditorMenu(display, menuManager, tableManagerService);
    Menu crudMenu = new CrudMenu(display, menuManager, dataService, tableManagerService);

    menuMap.put(mainMenu.getMenuType(), mainMenu);
    menuMap.put(settingMenu.getMenuType(), settingMenu);
    menuMap.put(tableEditorMenu.getMenuType(), tableEditorMenu);
    menuMap.put(crudMenu.getMenuType(), crudMenu);
  }

  @Override
  public EnumMap<MenuType, Menu> getMenuMap() {
    return this.menuMap;
  }
}
