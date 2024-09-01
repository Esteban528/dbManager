package com.javadbmanager.dataTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.javadbmanager.data.ConnectionBean;

import static org.junit.jupiter.api.Assertions.*;

class ConnectionBeanTest {

  private ConnectionBean connectionBean;

  @BeforeEach
  void setUp() {
    connectionBean = new ConnectionBean();
  }

  @Test
  void testDefaultConstructor() {
    assertNotNull(connectionBean);
    assertNull(connectionBean.getHost());
    assertNull(connectionBean.getPort());
    assertNull(connectionBean.getUsername());
    assertNull(connectionBean.getPassword());
    assertEquals("MySQL", connectionBean.getDBType());
    assertEquals(5.0, connectionBean.getDBversion());
    assertNull(connectionBean.getDatabase());
  }

  @Test
  void testParameterizedConstructor() {
    connectionBean = new ConnectionBean("localhost", "3306", "user", "pass", "PostgreSQL", 13.0, "testdb");

    assertEquals("localhost", connectionBean.getHost());
    assertEquals("3306", connectionBean.getPort());
    assertEquals("user", connectionBean.getUsername());
    assertEquals("pass", connectionBean.getPassword());
    assertEquals("PostgreSQL", connectionBean.getDBType());
    assertEquals(13.0, connectionBean.getDBversion());
    assertEquals("testdb", connectionBean.getDatabase());
  }

  @Test
  void testSetGetHost() {
    connectionBean.setHost("192.168.1.1");
    assertEquals("192.168.1.1", connectionBean.getHost());
  }

  @Test
  void testSetGetPortAsString() {
    connectionBean.setPort("5432");
    assertEquals("5432", connectionBean.getPort());
  }

  @Test
  void testSetGetPortAsInt() {
    connectionBean.setPort(5432);
    assertEquals("5432", connectionBean.getPort());
  }

  @Test
  void testSetGetUsername() {
    connectionBean.setUsername("admin");
    assertEquals("admin", connectionBean.getUsername());
  }

  @Test
  void testSetGetPassword() {
    connectionBean.setPassword("secret");
    assertEquals("secret", connectionBean.getPassword());
  }

  @Test
  void testSetGetDBType() {
    connectionBean.setDBType("Oracle");
    assertEquals("Oracle", connectionBean.getDBType());
  }

  @Test
  void testSetGetDBversion() {
    connectionBean.setDBversion(12.2);
    assertEquals(12.2, connectionBean.getDBversion());
  }

  @Test
  void testSetGetDatabase() {
    connectionBean.setDatabase("mydb");
    assertEquals("mydb", connectionBean.getDatabase());
  }
}
