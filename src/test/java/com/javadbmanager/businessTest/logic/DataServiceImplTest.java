package com.javadbmanager.businessTest.logic;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.javadbmanager.business.logic.DataLayerProvider;
import com.javadbmanager.business.logic.DataServiceImpl;
import com.javadbmanager.business.logic.exceptions.BusinessException;
import com.javadbmanager.data.AnyRepository;
import com.javadbmanager.data.ConnectionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataServiceImplTest {

  private ConnectionHandler connectionHandler;
  private AnyRepository anyRepository;
  private DataLayerProvider dataLayerProvider;
  private DataServiceImpl dataServiceImpl;

  @BeforeEach
  void setUp() {
    connectionHandler = mock(ConnectionHandler.class);
    anyRepository = mock(AnyRepository.class);
    dataLayerProvider = mock(DataLayerProvider.class);

    when(dataLayerProvider.getConnectionHandler()).thenReturn(connectionHandler);
    when(dataLayerProvider.getAnyRepository()).thenReturn(anyRepository);

    dataServiceImpl = new DataServiceImpl(dataLayerProvider);
  }

  @Test
  void testSetAndGetTableName() {
    String tableName = "my_table";
    dataServiceImpl.setTableName(tableName);
    assertEquals(tableName, dataServiceImpl.getTableName());
  }

  @Test
  void testInsertSuccess() throws Exception {
    Map<String, String> items = new HashMap<>();
    when(anyRepository.insert(items)).thenReturn(1);

    int rowsInserted = dataServiceImpl.insert(items);
    assertEquals(1, rowsInserted);
    verify(anyRepository).insert(items);
  }

  @Test
  void testInsertThrowsBusinessException() throws Exception {
    Map<String, String> items = new HashMap<>();
    when(anyRepository.insert(items)).thenThrow(new SQLException("SQL error"));

    assertThrows(BusinessException.class, () -> dataServiceImpl.insert(items));
    verify(anyRepository).insert(items);
  }

  @Test
  void testGetSuccess() throws Exception {
    List<Map<String, String>> mockResults = Arrays.asList(new HashMap<>());
    when(anyRepository.get()).thenReturn(mockResults);

    List<Map<String, String>> results = dataServiceImpl.get();
    assertEquals(mockResults, results);
    verify(anyRepository).get();
  }

  @Test
  void testGetThrowsBusinessException() throws Exception {
    when(anyRepository.get()).thenThrow(new SQLException("SQL error"));

    assertThrows(BusinessException.class, () -> dataServiceImpl.get());
    verify(anyRepository).get();
  }

  @Test
  void testGetWithWhereSuccess() throws Exception {
    Map<String, String> where = new HashMap<>();
    List<Map<String, String>> mockResults = Arrays.asList(new HashMap<>());
    when(anyRepository.get(where)).thenReturn(mockResults);

    List<Map<String, String>> results = dataServiceImpl.get(where);
    assertEquals(mockResults, results);
    verify(anyRepository).get(where);
  }

  @Test
  void testGetWithWhereThrowsBusinessException() throws Exception {
    Map<String, String> where = new HashMap<>();
    when(anyRepository.get(where)).thenThrow(new SQLException("SQL error"));

    assertThrows(BusinessException.class, () -> dataServiceImpl.get(where));
    verify(anyRepository).get(where);
  }

  @Test
  void testUpdateSuccess() throws Exception {
    Map<String, String> items = new HashMap<>();
    Map<String, String> wheres = new HashMap<>();
    when(anyRepository.update(items, wheres)).thenReturn(1);

    int rowsUpdated = dataServiceImpl.update(items, wheres);
    assertEquals(1, rowsUpdated);
    verify(anyRepository).update(items, wheres);
  }

  @Test
  void testUpdateThrowsBusinessException() throws Exception {
    Map<String, String> items = new HashMap<>();
    Map<String, String> wheres = new HashMap<>();
    when(anyRepository.update(items, wheres)).thenThrow(new SQLException("SQL error"));

    assertThrows(BusinessException.class, () -> dataServiceImpl.update(items, wheres));
    verify(anyRepository).update(items, wheres);
  }

  @Test
  void testDeleteSuccess() throws Exception {
    Map<String, String> where = new HashMap<>();
    when(anyRepository.delete(where)).thenReturn(1);

    int rowsDeleted = dataServiceImpl.delete(where);
    assertEquals(1, rowsDeleted);
    verify(anyRepository).delete(where);
  }

  @Test
  void testDeleteThrowsBusinessException() throws Exception {
    Map<String, String> where = new HashMap<>();
    when(anyRepository.delete(where)).thenThrow(new SQLException("SQL error"));

    assertThrows(BusinessException.class, () -> dataServiceImpl.delete(where));
    verify(anyRepository).delete(where);
  }

  @Test
  void testCommitSuccess() throws Exception {
    doNothing().when(anyRepository).commitChange();

    dataServiceImpl.commit();
    verify(anyRepository).commitChange();
  }

  @Test
  void testCommitThrowsBusinessException() throws Exception {
    doThrow(new SQLException("SQL error")).when(anyRepository).commitChange();

    assertThrows(BusinessException.class, () -> dataServiceImpl.commit());
    verify(anyRepository).commitChange();
  }

  @Test
  void testCommitAndCloseSuccess() throws Exception {
    doNothing().when(anyRepository).commitChange();
    doNothing().when(connectionHandler).close();

    dataServiceImpl.commitAndClose();
    verify(anyRepository).commitChange();
    verify(connectionHandler).close();
  }

  @Test
  void testCommitAndCloseThrowsBusinessExceptionOnCommit() throws Exception {
    doThrow(new SQLException("SQL error")).when(anyRepository).commitChange();

    assertThrows(BusinessException.class, () -> dataServiceImpl.commitAndClose());
    verify(anyRepository).commitChange();
    verify(connectionHandler, never()).close();
  }

  @Test
  void testCommitAndCloseThrowsBusinessExceptionOnClose() throws Exception {
    doNothing().when(anyRepository).commitChange();
    doThrow(new SQLException("SQL error")).when(connectionHandler).close();

    assertThrows(BusinessException.class, () -> dataServiceImpl.commitAndClose());
    verify(anyRepository).commitChange();
    verify(connectionHandler).close();
  }
}
