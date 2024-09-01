package com.javadbmanager.businessTest.logic.events;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.javadbmanager.business.logic.events.Command;
import com.javadbmanager.business.logic.events.Event;
import com.javadbmanager.business.logic.events.EventType;

class EventTest {

  @Test
  void testEventCreation() {
    EventType eventType = EventType.Test;
    Command command = mock(Command.class);

    Event event = new Event(eventType, command);

    assertEquals(eventType, event.getEventType());
    assertEquals(command, event.getCommand());
  }

  @Test
  void testEquals() {
    EventType eventType = EventType.Test;
    Command command1 = mock(Command.class);

    Event event1 = new Event(eventType, command1);
    Event event2 = new Event(eventType, command1);

    assertEquals(event1, event2);
  }

  @Test
  void testHashCode() {
    EventType eventType = EventType.Test;
    Command command = mock(Command.class);

    Event event1 = new Event(eventType, command);
    Event event2 = new Event(eventType, command);

    assertEquals(event1.hashCode(), event2.hashCode());
  }
}
