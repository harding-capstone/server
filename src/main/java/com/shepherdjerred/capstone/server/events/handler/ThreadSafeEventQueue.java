package com.shepherdjerred.capstone.server.events.handler;

import com.shepherdjerred.capstone.server.events.Event;
import java.util.HashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ThreadSafeEventQueue {

  private final Queue<Event> queue;
  private final Map<Class<? extends Event>, Set<EventHandler>> handlers;

  public ThreadSafeEventQueue() {
    queue = new ConcurrentLinkedQueue<>();
    handlers = new ConcurrentHashMap<>();
  }

  public <T extends Event> void registerHandler(Class<T> eventClass, EventHandler<T> handler) {
    var existingHandlers = getHandlers(eventClass);
    existingHandlers.add(handler);
    handlers.put(eventClass, existingHandlers);
  }

  public Set<EventHandler> getHandlers(Class<? extends Event> eventClass) {
    return handlers.getOrDefault(eventClass, new HashSet<>());
  }

  public void handleEvent() {
    if (!isEmpty()) {
      var event = queue.remove();

      var handlers = getHandlers(event.getClass());
      handlers.forEach(handler -> handler.handle(event));

      var genericHandles = getHandlers(Event.class);
      genericHandles.forEach(handler -> handler.handle(event));
    }
  }

  public boolean isEmpty() {
    return queue.isEmpty();
  }

  public void dispatchEvent(Event event) {
    queue.add(event);
  }
}
