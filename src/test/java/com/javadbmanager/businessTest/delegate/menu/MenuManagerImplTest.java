package com.javadbmanager.businessTest.delegate.menu;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.javadbmanager.business.delegate.menu.CommandHandler;
import com.javadbmanager.business.delegate.menu.Menu;
import com.javadbmanager.business.delegate.menu.MenuManagerImpl;
import com.javadbmanager.business.delegate.menu.MenuType;
import com.javadbmanager.business.delegate.menu.exceptions.MenuException;
import com.javadbmanager.presentation.Display;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MenuManagerImplTest {

  @Mock
  private Menu mockMenu;

  @Mock
  private Display mockDisplay;

  @Mock
  private CommandHandler mockCommandHandler;

  @Mock
  private Map<Integer, CommandHandler> mockOptions;

  @InjectMocks
  private MenuManagerImpl menuManager;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    when(mockMenu.getMenuType()).thenReturn(MenuType.Test);
    when(mockMenu.getOptions()).thenReturn(mockOptions);
    when(mockOptions.get(anyInt())).thenReturn(mockCommandHandler);
    when(mockCommandHandler.getTitle()).thenReturn("Test Command");

  }

  // @Test
  void testSetCurrentMenu() {
    // Arrange
    menuManager.addMenu(mockMenu);

    // Act
    menuManager.setCurrentMenu(MenuType.Test);

    // Assert
    assertEquals(mockMenu, menuManager.getCurrentMenu());
    assertEquals(MenuType.Test, menuManager.getCurrentMenuType());
  }

  @Test
  void testLoadWhenMenuIsNotBusy() throws Exception {
    // Arrange
    when(mockMenu.isBusy()).thenReturn(false);
    when(mockMenu.getTitle()).thenReturn("Test");
    when(mockMenu.getMenuType()).thenReturn(MenuType.Test);

    menuManager.addMenu(mockMenu);
    menuManager.setCurrentMenu(MenuType.Test);

    when(mockDisplay.scan()).thenReturn(1);
    // Act
    menuManager.load(MenuType.Test);

    // Assert
    verify(mockCommandHandler).execute();
  }

  @Test
  void testLoadWhenMenuIsBusy() {
    // Arrange
    when(mockMenu.isBusy()).thenReturn(true);
    when(mockMenu.getTitle()).thenReturn("Test");
    when(mockMenu.getMenuType()).thenReturn(MenuType.Test);

    menuManager.addMenu(mockMenu);
    menuManager.setCurrentMenu(MenuType.Test);

    // Act & Assert
    MenuException exception = assertThrows(MenuException.class, () -> menuManager.load(MenuType.Test));
    assertEquals("The current menu is busy", exception.getMessage());
    verify(mockDisplay).sendLog("The current menu is busy");
  }
}
