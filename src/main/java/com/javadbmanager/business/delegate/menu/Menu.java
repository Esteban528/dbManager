package com.javadbmanager.business.delegate.menu;

import java.util.Map;
import java.util.TreeMap;

import com.javadbmanager.business.logic.events.Command;
import com.javadbmanager.presentation.Display;

public class Menu {
  private final Map<Integer, CommandHandler> options;
  protected Display display;
  protected String title;
  protected String description;
  protected MenuManager menuManager;
  protected boolean busy;
  protected MenuType menuType;

  public Menu(String title, String description, MenuType menuType, Display display, MenuManager menuManager) {
    this.title = title;
    this.description = description;
    this.menuType = menuType;
    this.options = new TreeMap<>();
    this.display = display;
    this.menuManager = menuManager;
    this.busy = false;
  }

  public void addOption(int index, String title, Command command) {
    CommandHandlerImpl commandHandler = CommandHandlerImpl.commandBuilder()
        .setTitle(title)
        .setCommand(command)
        .build();

    options.put(index, commandHandler);
  }

  public void addOption(int index, CommandHandler commandHandler) {
    options.put(index, commandHandler);
  }

  public void removeOption(int index) {
    options.remove(index);
  }

  public Map<Integer, CommandHandler> getOptions() {
    return options;
  }

  public void executeOption(int index) {
    options.get(index).execute();
  }

  public Display getDisplay() {
    return display;
  }

  public void setDisplay(Display display) {
    this.display = display;
  }

  public MenuManager getMenuManager() {
    return menuManager;
  }

  public void setMenuManager(MenuManager menuManager) {
    this.menuManager = menuManager;
  }

  public boolean isBusy() {
    return busy;
  }

  public void setBusy(boolean busy) {
    this.busy = busy;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public MenuType getMenuType() {
    return menuType;
  }

  public void setMenuType(MenuType menuType) {
    this.menuType = menuType;
  }

  public void update() {

  }
}
