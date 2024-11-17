package com.javadbmanager.business.delegate.menu;

/**
 * Represents the different types of menus available in the application.
 */
public enum MenuType {
    /**
     * The main menu of the application. This is typically the starting point for
     * users.
     */
    MainMenu,

    /** The configuration menu, allowing users to adjust application settings. */
    Config,

    /**
     * The table management menu, used for operations related to database tables.
     */
    TableManager,

    /**
     * The CRUD (Create, Read, Update, Delete) operations menu for managing data.
     */
    CrudManager,

    /** A testing menu (likely for development or debugging purposes). */
    Test,

    /**
     * Represents an empty or non-existent menu. This is typically used as a
     * placeholder or initial value.
     */
    EmptyMenu
}
