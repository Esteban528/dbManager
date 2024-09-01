package com.javadbmanager.business.delegate.menu;

import java.util.Map;

import com.javadbmanager.business.delegate.menu.exceptions.MenuException;

public interface MenuManager {
  void setCurrentMenu(MenuType key);

  Menu getCurrentMenu();

  MenuType getCurrentMenuType();

  void addMenu(Menu menu);

  void removeMenu(MenuType key);

  Map<MenuType, Menu> getMenuList();

  void load(MenuType menuType) throws MenuException;
}
