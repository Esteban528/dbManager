package com.javadbmanager.business.logic.events;

import java.util.ArrayList;
import java.util.List;

public class EventManager {
  private static EventManager instance;
  private final List<Event> listenerList;

  public synchronized static EventManager getInstance() {
    if (instance == null) {
      instance = new EventManager();
    }

    return instance;
  }

  private EventManager() {
    listenerList = new ArrayList<>();
  }

  public void addEventHandler(Event event) {
    listenerList.add(event);
  }

  public void removeEventHandler(int i) {
    listenerList.remove(i);
  }

  public void removeEventHandler(Event event) {
    listenerList.remove(event);
  }

  public void call(EventType eventType) {
    listenerList.forEach(event -> {
      if (event.getEventType().equals(eventType))
        event.getCommand().execute();
    });
  }

  public List<Event> getListenerList() {
    return listenerList;
  }
}
