package com.javadbmanager.business.delegate.menu;

import java.util.EnumMap;
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
    this.currentMenu = getMenuMap().get(key);
    this.currentMenuType = getMenuMap().get(key).getMenuType();
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
  public Map<MenuType, Menu> getMenuMap() {
    return this.menuMap;
  }

  @Override
  public void load(MenuType menuType) throws MenuException {
    if (getCurrentMenuType() == MenuType.EmptyMenu || (!getCurrentMenu().isBusy())) {
      display.clean();

      setCurrentMenu(menuType);

      Menu menu = menuMap.get(menuType);
      Map<Integer, CommandHandler> options = menu.getOptions();
      display.sendLog(menu.getTitle());
      if (menuType != MenuType.MainMenu)
        display.sendLog("0. Main menu");
      options.keySet().forEach(key -> {
        CommandHandler commandHandler = options.get(key);
        display.sendLog(String.format("%d. \u001B[38;5;208m%s\u001B[0m", key, commandHandler.getTitle()));
      });

      try {
        display.sendLog("Select an option. (Only number)");

        int option = display.scan();
        display.sendLog("Selected: " + String.format("\u001B[38;5;208m%s\u001B[0m\n", option));

        if (option == 0)
          load(MenuType.MainMenu);
        else
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
