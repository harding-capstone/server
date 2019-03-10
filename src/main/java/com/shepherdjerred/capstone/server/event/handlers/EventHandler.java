package com.shepherdjerred.capstone.server.event.handlers;

import com.shepherdjerred.capstone.server.event.Event;

public interface EventHandler<T extends Event> {
  void handle(T event);
}
