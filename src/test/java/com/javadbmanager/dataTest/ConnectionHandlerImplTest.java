package com.javadbmanager.dataTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.javadbmanager.data.ConnectionBean;
import com.javadbmanager.data.ConnectionHandlerImpl;
import com.javadbmanager.data.utils.DataUtils;

public class ConnectionHandlerImplTest {

  @Mock
  private ConnectionBean connectionBean;

  @Mock
  private DataUtils dataUtils;

  @Mock
  private DataSource dataSource;

  @Mock
  private Connection connection;

  @InjectMocks
  private ConnectionHandlerImpl connectionHandler;

  @BeforeEach
  public void setUp() throws SQLException {
    MockitoAnnotations.openMocks(this);
    when(connectionBean.getUsername()).thenReturn("test_user");
    when(connectionBean.getPassword()).thenReturn("test_pass");
    when(dataUtils.makeUrl(connectionBean)).thenReturn("jdbc:h2:mem:testdb");

    when(dataSource.getConnection()).thenReturn(connection);
    when(connection.isClosed()).thenReturn(false);
    doNothing().when(connection).setAutoCommit(false);

    connectionHandler = spy(new ConnectionHandlerImpl(connectionBean, dataUtils));
    doReturn(dataSource).when(connectionHandler).getDataSource(any(ConnectionBean.class), anyString());
  }

  @Test
  public void testCreateConnection() throws SQLException {
    connectionHandler.createConnection();

    verify(connectionHandler, times(1)).createConnection();
    verify(dataSource, times(1)).getConnection();
    // verify(connection, times(1)).setAutoCommit(false);
    assertNotNull(connectionHandler.getConnection());
  }

  @Test
  @Disabled // Testless
  public void testGetConnection() throws SQLException {
    Connection conn = connectionHandler.getConnection();
    assertNotNull(conn);
    verify(connectionHandler, times(1)).createConnection();

    conn = connectionHandler.getConnection();
    assertNotNull(conn);
    verify(connectionHandler, times(1)).createConnection();
  }

  @Test
  public void testCloseConnection() throws SQLException {
    connectionHandler.createConnection();
    connectionHandler.close();
    verify(connection, times(1)).close();
  }
}
