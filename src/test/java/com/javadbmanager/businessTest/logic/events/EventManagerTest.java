package com.javadbmanager.businessTest.logic.events;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.javadbmanager.business.logic.events.Event;
import com.javadbmanager.business.logic.events.EventManager;
import com.javadbmanager.business.logic.events.EventType;

public class EventManagerTest {
  Event event;
  boolean bool = false;

  @BeforeEach
  void setUp() {
    event = mock(Event.class);

    setBool(false);
    when(event.getCommand()).thenReturn(() -> {
      setBool(true);
    });

    when(event.getEventType()).thenReturn(EventType.Test);
  }

  @AfterEach
  void setDown() {
    event = null;
  }

  @Test
  void getInstanceTest() {
    EventManager instance1 = EventManager.getInstance();
    EventManager instance2 = EventManager.getInstance();

    assertEquals(instance1, instance2);
  }

  @Test
  void addEventListenerAndRemoveEventListenerTest() {
    EventManager em = EventManager.getInstance();
    em.addEventHandler(event);

    assertEquals(event, em.getListenerList().get(0));

    em.removeEventHandler(event);
    assertThrows(IndexOutOfBoundsException.class, () -> em.getListenerList().get(0));
  }

  @Test
  void callTest() {
    EventManager em = EventManager.getInstance();
    em.addEventHandler(event);
    em.call(EventType.Test);
    assertTrue(getBool());
  }

  void setBool(boolean bool) {
    this.bool = bool;
  }

  boolean getBool() {
    return this.bool;
  }
}
