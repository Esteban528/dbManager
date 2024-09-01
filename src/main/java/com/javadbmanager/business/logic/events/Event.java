package com.javadbmanager.business.logic.events;

public class Event {
  private final EventType eventType;
  private final Command command;

  public Event(EventType eventType, Command command) {
    this.eventType = eventType;
    this.command = command;
  }

  public Command getCommand() {
    return command;
  }

  public EventType getEventType() {
    return eventType;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((eventType == null) ? 0 : eventType.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Event other = (Event) obj;
    if (eventType != other.eventType)
      return false;
    return true;
  }

}
