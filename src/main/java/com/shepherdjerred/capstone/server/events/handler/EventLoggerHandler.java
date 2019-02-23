package com.shepherdjerred.capstone.server.events.handler;

import com.shepherdjerred.capstone.server.events.Event;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class EventLoggerHandler implements EventHandler<Event> {

  @Override
  public void handle(Event event) {
    log.info(event);
  }
}
