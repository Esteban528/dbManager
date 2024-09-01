
package com.javadbmanager.businessTest.delegate.menu;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Map;
import java.util.TreeMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.javadbmanager.business.delegate.menu.CommandHandler;
import com.javadbmanager.business.delegate.menu.CommandHandlerImpl;
import com.javadbmanager.business.delegate.menu.Menu;
import com.javadbmanager.business.delegate.menu.MenuManager;
import com.javadbmanager.business.delegate.menu.MenuType;
import com.javadbmanager.presentation.Display;
import com.javadbmanager.presentation.DisplayCLI;

public class MenuTest {

  private CommandHandlerImpl commandHandlerImpl;
  private MenuManager menuManager;
  private Display display;
  private boolean bool;
  private final String title = "Connect DB";

  @BeforeEach
  void setUp() {
    commandHandlerImpl = mock(CommandHandlerImpl.class);
    menuManager = mock(MenuManager.class);
    display = mock(DisplayCLI.class);

    when(commandHandlerImpl.getTitle()).thenReturn(title);
    doAnswer(a -> {
      setBool(true);
      return null;
    }).when(commandHandlerImpl).execute();
  }

  @Test
  void constructorTest() {
    Menu menu = new Menu("Test Menu", "Test description", MenuType.Test, display, menuManager);
    Map<Integer, CommandHandler> expected = new TreeMap<>();

    assertEquals(expected, menu.getOptions());
    assertEquals(display, menu.getDisplay());
    assertEquals(menuManager, menu.getMenuManager());
    assertFalse(menu.isBusy());
  }

  @Test
  void addOptionTest() {
    Menu menu = new Menu("Test Menu", "Test description", MenuType.Test, display, menuManager);

    // Agregar opción usando el título y el comando
    menu.addOption(1, title, () -> {
    });
    assertEquals(title, menu.getOptions().get(1).getTitle());

    // Agregar opción usando CommandHandler
    menu.addOption(2, commandHandlerImpl);
    assertEquals(commandHandlerImpl, menu.getOptions().get(2));
  }

  @Test
  void removeOptionTest() {
    Menu menu = new Menu("Test Menu", "Test description", MenuType.Test, display, menuManager);

    menu.addOption(1, commandHandlerImpl);
    menu.removeOption(1);

    assertNull(menu.getOptions().get(1));
  }

  @Test
  void executeOptionTest() {
    Menu menu = new Menu("Test Menu", "Test description", MenuType.Test, display, menuManager);

    setBool(false);
    menu.addOption(1, commandHandlerImpl);
    menu.executeOption(1);

    assertTrue(getBool());
  }

  @Test
  void testDisplaySetterGetter() {
    Menu menu = new Menu("Test Menu", "Test description", MenuType.Test, display, menuManager);
    Display newDisplay = mock(Display.class);

    menu.setDisplay(newDisplay);
    assertEquals(newDisplay, menu.getDisplay());
  }

  @Test
  void testMenuManagerSetterGetter() {
    Menu menu = new Menu("Test Menu", "Test description", MenuType.Test, display, menuManager);
    MenuManager newMenuManager = mock(MenuManager.class);

    menu.setMenuManager(newMenuManager);
    assertEquals(newMenuManager, menu.getMenuManager());
  }

  @Test
  void testBusySetterGetter() {
    Menu menu = new Menu("Test Menu", "Test description", MenuType.Test, display, menuManager);

    menu.setBusy(true);
    assertTrue(menu.isBusy());

    menu.setBusy(false);
    assertFalse(menu.isBusy());
  }

  private void setBool(boolean bool) {
    this.bool = bool;
  }

  private boolean getBool() {
    return this.bool;
  }
}
