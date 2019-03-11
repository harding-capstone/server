package com.shepherdjerred.capstone.server.event.handlers;

import com.shepherdjerred.capstone.server.event.Event;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class EventLoggerHandler<T extends Event> implements EventHandler<T> {

  @Override
  public void handle(T event) {
    log.info(event);
  }
}
