package com.javadbmanager.businessTest.delegate.menu.menus;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

import java.util.Map;

import com.javadbmanager.business.delegate.menu.CommandHandler;
import com.javadbmanager.business.delegate.menu.Menu;
import com.javadbmanager.business.delegate.menu.MenuManagerImpl;
import com.javadbmanager.business.delegate.menu.MenuType;
import com.javadbmanager.business.delegate.menu.menus.MainMenu;
import com.javadbmanager.business.logic.ConnectionBeanBuilder;
import com.javadbmanager.business.logic.EnvManagerServiceImpl;
import com.javadbmanager.presentation.DisplayCLI;

public class MainMenuTest {
  MainMenu mainMenu;

  @Mock
  DisplayCLI display;

  @Mock
  MenuManagerImpl menuManager;

  @Mock
  ConnectionBeanBuilder connectionBeanBuilder;

  @Mock
  EnvManagerServiceImpl envManagerService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    when(envManagerService.get("ConnectionBean")).thenReturn(true);
    mainMenu = new MainMenu(display, menuManager, connectionBeanBuilder, envManagerService);
  }

  @AfterEach
  void tearDown() {
    mainMenu = null;
  }

  @Test
  void constructorTest() {
    Menu menu = new Menu("Test", "Test", MenuType.Test, display, menuManager);

    assertInstanceOf(menu.getClass(), mainMenu);
  }

  @Test
  void testOptions() {

    Map<Integer, CommandHandler> optionMap = mainMenu.getOptions();
    optionMap.get(1).execute();
    verify(menuManager).load(MenuType.Config);

    optionMap.get(2).execute();
    verify(menuManager).load(MenuType.TableManager);
  }
}
