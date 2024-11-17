package com.javadbmanager.business.delegate.menu;

import java.util.Map;
import java.util.TreeMap;

import com.javadbmanager.business.logic.events.Command;
import com.javadbmanager.presentation.Display;

/**
 * Represents a menu in the application's user interface. A menu consists of a title,
 * a description, a set of options (represented by {@code CommandHandler} objects),
 * and references to the {@code Display} and {@code MenuManager} for interaction and navigation.
 */
public class Menu {
  private final Map<Integer, CommandHandler> options;
  protected Display display;
  protected String title;
  protected String description;
  protected MenuManager menuManager;
  protected boolean busy;
  protected MenuType menuType;

  /**
   * Constructs a new {@code Menu} with the given title, description, menu type, display, and menu manager.
   * @param title The title of the menu.
   * @param description A brief description of the menu's purpose.
   * @param menuType The type of menu (used for identification and management).
   * @param display The {@code Display} object for user interaction.
   * @param menuManager The {@code MenuManager} for handling menu navigation.
   */
  public Menu(String title, String description, MenuType menuType, Display display, MenuManager menuManager) {
    this.title = title;
    this.description = description;
    this.menuType = menuType;
    this.options = new TreeMap<>(); // Using a TreeMap to maintain order by index
    this.display = display;
    this.menuManager = menuManager;
    this.busy = false;
  }

  /**
   * Adds a new option to the menu using a {@code Command}.
   * @param index The numerical index of the option in the menu.
   * @param title The title or label of the option.
   * @param command The {@code Command} to be executed when the option is selected.
   */
  public void addOption(int index, String title, Command command) {
    CommandHandlerImpl commandHandler = CommandHandlerImpl.commandBuilder()
        .setTitle(title)
        .setCommand(command)
        .build();

    options.put(index, commandHandler);
  }

  /**
   * Adds a pre-built {@code CommandHandler} as an option to the menu.
   * @param index The numerical index of the option in the menu.
   * @param commandHandler The {@code CommandHandler} for the option.
   */
  public void addOption(int index, CommandHandler commandHandler) {
    options.put(index, commandHandler);
  }

  /**
   * Removes an option from the menu.
   * @param index The index of the option to remove.
   */
  public void removeOption(int index) {
    options.remove(index);
  }

  /**
   * Returns the options of this menu.
   * @return A map where keys are option indices and values are the corresponding {@code CommandHandler} objects. The map is sorted by index.
   */
  public Map<Integer, CommandHandler> getOptions() {
    return options;
  }

  /**
   * Executes the command associated with the option at the given index.
   * @param index The index of the option to execute.
   * @throws NullPointerException If there is no option with the given index.
   */
  public void executeOption(int index) {
    options.get(index).execute();
  }

  /**
   * Gets the display used by this menu.
   * @return The display.
   */
  public Display getDisplay() {
    return display;
  }

  /**
   * Sets the display for this menu.
   * @param display The new display to use.
   */
  public void setDisplay(Display display) {
    this.display = display;
  }

  /**
   * Retrieves the menu manager associated with this menu.
   * @return The menu manager.
   */
  public MenuManager getMenuManager() {
    return menuManager;
  }

  /**
   * Sets the menu manager for this menu.
   * @param menuManager The new menu manager to use.
   */
  public void setMenuManager(MenuManager menuManager) {
    this.menuManager = menuManager;
  }

  /**
   * Checks if the menu is currently busy (e.g., processing a long-running operation).
   * @return {@code true} if the menu is busy, {@code false} otherwise.
   */
  public boolean isBusy() {
    return busy;
  }

  /**
   * Sets the busy status of the menu.
   * @param busy {@code true} to mark the menu as busy, {@code false} otherwise.
   */
  public void setBusy(boolean busy) {
    this.busy = busy;
  }

  /**
   * Gets the title of the menu.
   * @return The title.
   */
  public String getTitle() {
    return title;
  }

  /**
   * Sets the title of the menu.
   * @param title The new title.
   */
  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * Gets the description of the menu.
   * @return The description.
   */
  public String getDescription() {
    return description;
  }

  /**
   * Sets the description of the menu.
   * @param description The new description.
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * Gets the type of the menu.
   * @return The menu type.
   */
  public MenuType getMenuType() {
    return menuType;
  }

  /**
   * Sets the type of the menu.
   * @param menuType The new menu type.
   */
  public void setMenuType(MenuType menuType) {
    this.menuType = menuType;
  }

  /**
   * A placeholder method for updating the menu's state or content. It can be
   * overridden in subclasses to implement dynamic updates.
   */
  public void update() {

  }
}
