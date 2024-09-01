package com.javadbmanager.business.delegate.menu;

import java.util.EnumMap;

public interface MenuProvider {
  EnumMap<MenuType, Menu> getMenuMap();
}
