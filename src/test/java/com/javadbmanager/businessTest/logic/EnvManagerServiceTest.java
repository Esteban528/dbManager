package com.javadbmanager.businessTest.logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.javadbmanager.business.logic.EnvManagerService;
import com.javadbmanager.business.logic.EnvManagerServiceImpl;

import static org.junit.jupiter.api.Assertions.*;

class EnvManagerServiceImplTest {

  private EnvManagerService envManagerService;

  @BeforeEach
  void setUp() {
    envManagerService = EnvManagerServiceImpl.getInstance();
    envManagerService.set("TEST_ENV", "testValue");
  }

  @Test
  void testSingletonInstance() {
    EnvManagerService anotherInstance = EnvManagerServiceImpl.getInstance();
    assertSame(envManagerService, anotherInstance);
  }

  @Test
  void testSetAndGetEnvironmentVariable() {
    envManagerService.set("NEW_ENV", "newValue");
    Object value = envManagerService.get("NEW_ENV");
    assertEquals("newValue", value);
  }

  @Test
  void testGetNonExistingEnvironmentVariable() {
    Object value = envManagerService.get("NON_EXISTING_ENV");
    assertNull(value);
  }

  @Test
  void testRemoveEnvironmentVariable() {
    envManagerService.remove("TEST_ENV");
    Object value = envManagerService.get("TEST_ENV");
    assertNull(value);
  }

  @Test
  void testOverwriteEnvironmentVariable() {
    envManagerService.set("TEST_ENV", "newTestValue");
    Object value = envManagerService.get("TEST_ENV");
    assertEquals("newTestValue", value);
  }

  @Test
  void testRemoveNonExistingEnvironmentVariable() {
    assertDoesNotThrow(() -> envManagerService.remove("NON_EXISTING_ENV"));
  }
}
