package com.javadbmanager.businessTest.delegate.menu.menus;

import static org.junit.jupiter.api.Assertions.*;

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
import com.javadbmanager.business.delegate.menu.menus.SettingMenu;
import com.javadbmanager.business.logic.ConnectionBeanBuilder;
import com.javadbmanager.business.logic.EnvManagerServiceImpl;
import com.javadbmanager.business.logic.utils.ConnectionUtil;
import com.javadbmanager.data.ConnectionBean;
import com.javadbmanager.presentation.DisplayCLI;

public class SettingMenuTest {
  SettingMenu settingMenu;

  @Mock
  DisplayCLI display;

  @Mock
  MenuManagerImpl menuManager;

  @Mock
  ConnectionBeanBuilder connectionBeanBuilder;

  @Mock
  EnvManagerServiceImpl envManagerService;

  @Mock
  ConnectionUtil connectionUtil;

  @Mock
  ConnectionBean connectionBean;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    when(connectionBeanBuilder.setHost(anyString())).thenReturn(connectionBeanBuilder);
    when(connectionBeanBuilder.setPort(anyString())).thenReturn(connectionBeanBuilder);
    when(connectionBeanBuilder.setUsername(anyString())).thenReturn(connectionBeanBuilder);
    when(connectionBeanBuilder.setPassword(anyString())).thenReturn(connectionBeanBuilder);
    when(connectionBeanBuilder.setDBType(anyString())).thenReturn(connectionBeanBuilder);
    when(connectionBeanBuilder.setDBVersion(anyDouble())).thenReturn(connectionBeanBuilder);
    when(connectionBeanBuilder.setDatabase(anyString())).thenReturn(connectionBeanBuilder);
    when(connectionBeanBuilder.build()).thenReturn(connectionBean);

    when(display.scanLine())
        .thenReturn("localhost") // host
        .thenReturn("5432") // port
        .thenReturn("user") // username
        .thenReturn("password") // password
        .thenReturn("MySql") // dbType
        .thenReturn("my_database"); // database

    when(display.scanDouble())
        .thenReturn(5.0);

    when(envManagerService.get("ConnectionBean")).thenReturn(true);
  }

  @Test
  void constructorTest() {
    SettingMenu settingMenu = new SettingMenu(display, menuManager, connectionBeanBuilder, connectionUtil,
        envManagerService);
    Menu menu = new Menu("Test", "Test", MenuType.Test, display, menuManager);

    assertInstanceOf(menu.getClass(), settingMenu);
  }

  @Test
  void testOptions() {

    SettingMenu settingMenu = new SettingMenu(display, menuManager, connectionBeanBuilder, connectionUtil,
        envManagerService);

    Map<Integer, CommandHandler> options = settingMenu.getOptions();

    when(connectionUtil.test(any(ConnectionBeanBuilder.class)))
        .thenReturn(true);
    options.get(1).execute();

    verify(connectionBeanBuilder).setHost(anyString());
    verify(connectionBeanBuilder).setPort(anyString());
    verify(connectionBeanBuilder).setUsername(anyString());
    verify(connectionBeanBuilder).setPassword(anyString());
    verify(connectionBeanBuilder).setDBType(anyString());
    verify(connectionBeanBuilder).setDBVersion(anyDouble());
    verify(connectionBeanBuilder).setDatabase(anyString());
    verify(connectionBeanBuilder).build();
    assertFalse(settingMenu.isBusy());
  }

  @Test
  void testOptionsWithExceptions() {

    SettingMenu settingMenu = new SettingMenu(display, menuManager, connectionBeanBuilder, connectionUtil,
        envManagerService);

    Map<Integer, CommandHandler> options = settingMenu.getOptions();

    when(connectionUtil.test(connectionBeanBuilder))
        .thenReturn(false);
    options.get(1).execute();

    verify(connectionBeanBuilder).setHost(anyString());
    verify(connectionBeanBuilder).setPort(anyString());
    verify(connectionBeanBuilder).setUsername(anyString());
    verify(connectionBeanBuilder).setPassword(anyString());
    verify(connectionBeanBuilder).setDBType(anyString());
    verify(connectionBeanBuilder).setDBVersion(anyDouble());
    verify(connectionBeanBuilder).setDatabase(anyString());
    verify(connectionBeanBuilder, never()).build();

    assertFalse(settingMenu.isBusy());
  }
}
