package com.shepherdjerred.capstone.server.event;

import com.shepherdjerred.capstone.server.event.handlers.EventHandler;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Non-thread-safe event bus.
 */
public class EventBus<T extends Event> {

  private final Set<EventHandler<T>> genericHandlers;
  private final Map<Class<T>, Set<EventHandler<T>>> handlers;

  public EventBus() {
    this.genericHandlers = new HashSet<>();
    this.handlers = new HashMap<>();
  }

  @SuppressWarnings("unchecked")
  public <U extends T> void registerHandler(Class<U> eventClass, EventHandler<U> handler) {
    var existingHandlers = getHandlers((Class<T>) eventClass);
    existingHandlers.add((EventHandler<T>) handler);
    handlers.put((Class<T>) eventClass, existingHandlers);
  }

  public void registerHandler(EventHandler<T> handler) {
    genericHandlers.add(handler);
  }

  @SuppressWarnings("unchecked")
  public void dispatch(T event) {
    var handlers = getHandlers((Class<T>) event.getClass());
    handlers.forEach(handler -> handler.handle(event));
    genericHandlers.forEach(handler -> handler.handle(event));
  }

  private Set<EventHandler<T>> getHandlers(Class<T> eventClass) {
    return handlers.getOrDefault(eventClass, new HashSet<>());
  }
}
