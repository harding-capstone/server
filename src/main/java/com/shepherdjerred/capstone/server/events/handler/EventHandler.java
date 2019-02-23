package com.shepherdjerred.capstone.server.events.handler;

import com.shepherdjerred.capstone.server.events.Event;

public interface EventHandler<T extends Event> {
  void handle(T event);
}
