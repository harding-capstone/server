package com.shepherdjerred.capstone.server.event.handlers;

import com.shepherdjerred.capstone.server.event.Event;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class EventLoggerHandler implements EventHandler<Event> {

  @Override
  public void handle(Event event) {
    log.info(event);
  }
}
