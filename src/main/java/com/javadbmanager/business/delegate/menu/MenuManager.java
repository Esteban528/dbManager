package com.javadbmanager.business.delegate.menu;

import java.util.Map;

import com.javadbmanager.business.delegate.menu.exceptions.MenuException;

/**
 * The {@code MenuManager} interface is responsible for orchestrating and managing the application's menus.
 * It provides methods for adding, removing, loading, and navigating between different menus.
 */
public interface MenuManager {

    /**
     * Sets the current active menu.
     * @param key The {@code MenuType} representing the menu to be set as current.
     */
    void setCurrentMenu(MenuType key);

    /**
     * Retrieves the currently active menu.
     * @return The current {@code Menu} object, or {@code null} if no menu is active.
     */
    Menu getCurrentMenu();

    /**
     * Retrieves the {@code MenuType} of the currently active menu.
     * @return The {@code MenuType} of the current menu, or {@code null} if no menu is active.
     */
    MenuType getCurrentMenuType();

    /**
     * Adds a menu to the managed collection of menus.
     * @param menu The {@code Menu} object to be added.
     */
    void addMenu(Menu menu);

    /**
     * Removes a menu from the managed collection of menus.
     * @param key The {@code MenuType} of the menu to be removed.
     */
    void removeMenu(MenuType key);

    /**
     * Retrieves the map of all managed menus, keyed by their {@code MenuType}.
     * @return An unmodifiable {@code Map} containing all menus, with {@code MenuType} as keys
     *         and {@code Menu} objects as values. Returning an unmodifiable map prevents
     *         external modification of the menu structure.
     */
    Map<MenuType, Menu> getMenuMap();


    /**
     * Loads and activates the specified menu. This might involve initializing the menu's
     * components, displaying its content, and setting it as the current menu.
     * @param menuType The {@code MenuType} of the menu to be loaded.
     * @throws MenuException if an error occurs during menu loading, such as if the
     *                        specified menu is not found or cannot be initialized.
     */
    void load(MenuType menuType) throws MenuException;
}
