package com.javadbmanager.businessTest.logic;

import com.javadbmanager.business.logic.ConnectionBeanBuilder;
import com.javadbmanager.data.ConnectionBean;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConnectionBeanBuilderTest {

  @Test
  void testBuildWithDefaultValues() {
    ConnectionBean connectionBean = new ConnectionBeanBuilder().build();

    assertEquals("localhost", connectionBean.getHost());
    assertEquals("3306", connectionBean.getPort());
    assertEquals("root", connectionBean.getUsername());
    assertEquals("", connectionBean.getPassword());
    assertEquals("mysql", connectionBean.getDBType());
    assertEquals(8.1, connectionBean.getDBversion());
    assertEquals("users", connectionBean.getDatabase());
  }

  @Test
  void testBuildWithCustomValues() {
    ConnectionBean connectionBean = new ConnectionBeanBuilder()
        .setHost("192.168.1.100")
        .setPort("5432")
        .setUsername("admin")
        .setPassword("securepassword")
        .setDBType("postgresql")
        .setDBVersion(13.4)
        .setDatabase("testdb")
        .build();

    assertEquals("192.168.1.100", connectionBean.getHost());
    assertEquals("5432", connectionBean.getPort());
    assertEquals("admin", connectionBean.getUsername());
    assertEquals("securepassword", connectionBean.getPassword());
    assertEquals("postgresql", connectionBean.getDBType());
    assertEquals(13.4, connectionBean.getDBversion());
    assertEquals("testdb", connectionBean.getDatabase());
  }

  @Test
  void testBuildWithPartialCustomValues() {
    ConnectionBean connectionBean = new ConnectionBeanBuilder()
        .setHost("127.0.0.1")
        .setUsername("customUser")
        .build();

    assertEquals("127.0.0.1", connectionBean.getHost());
    assertEquals("3306", connectionBean.getPort()); // Default value
    assertEquals("customUser", connectionBean.getUsername());
    assertEquals("", connectionBean.getPassword()); // Default value
    assertEquals("mysql", connectionBean.getDBType()); // Default value
    assertEquals(8.1, connectionBean.getDBversion()); // Default value
    assertEquals("users", connectionBean.getDatabase()); // Default value
  }
}
