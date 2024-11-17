package com.javadbmanager.business.delegate.menu;

import java.util.List;

/**
 * The {@code MenuProvider} interface defines a method for retrieving a list of menus.
 * Implementations of this interface are responsible for creating and configuring the
 * menus that will be used in the MenuManager.
 */
public interface MenuProvider {

    /**
     * Retrieves a list of {@code Menu} objects.
     * @return A list of configured menus. This list should be ready to be used by
     *         a {@code MenuManager} to handle menu display and navigation.
     */
    List<Menu> getMenuMap(); // Consider renaming this to getMenus() for clarity
}
