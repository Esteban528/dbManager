package com.javadbmanager.business.delegate.menu;

import java.util.EnumMap;
import java.util.Iterator;
import java.util.Map;

import com.javadbmanager.business.delegate.menu.exceptions.MenuException;
import com.javadbmanager.presentation.Display;
import com.javadbmanager.presentation.exceptions.EmptyValueException;

public class MenuManagerImpl implements MenuManager {
  private Menu currentMenu;
  private MenuType currentMenuType;
  private final Map<MenuType, Menu> menuMap;
  private Display display;

  public MenuManagerImpl(Display display) {
    this.display = display;
    menuMap = new EnumMap<>(MenuType.class);
    currentMenuType = MenuType.EmptyMenu;
  }

  @Override
  public void setCurrentMenu(MenuType key) {
    this.currentMenu = getMenuList().get(key);
    this.currentMenuType = getMenuList().get(key).getMenuType();
  }

  @Override
  public Menu getCurrentMenu() {
    return this.currentMenu;
  }

  @Override
  public MenuType getCurrentMenuType() {
    return this.currentMenuType;
  }

  @Override
  public void addMenu(Menu menu) {
    menuMap.put(menu.getMenuType(), menu);
  }

  @Override
  public void removeMenu(MenuType key) {
    menuMap.remove(key);
  }

  @Override
  public Map<MenuType, Menu> getMenuList() {
    return this.menuMap;
  }

  @Override
  public void load(MenuType menuType) throws MenuException {
    if (getCurrentMenuType() == MenuType.EmptyMenu || (getCurrentMenu() != null && !getCurrentMenu().isBusy())) {
      display.clean();

      setCurrentMenu(menuType);

      Menu menu = menuMap.get(menuType);
      Map<Integer, CommandHandler> options = menu.getOptions();

      Iterator<Integer> iterator = options.keySet().iterator();
      while (iterator.hasNext()) {
        int key = 1;
        CommandHandler commandHandler = options.get(key);
        display.addLog(String.format("%d. %s", key, commandHandler.getTitle()));
      }
      display.show();

      try {
        int option = display.scan();

        options.get(option).execute();

      } catch (EmptyValueException e) {
        load(menuType);
      }
    } else {
      String message = "The current menu is busy";
      display.sendLog(message);
      throw new MenuException(message);
    }
  }
}
