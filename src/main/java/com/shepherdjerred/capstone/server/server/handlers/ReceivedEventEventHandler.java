package com.shepherdjerred.capstone.server.server.handlers;

import com.shepherdjerred.capstone.server.events.Event;
import com.shepherdjerred.capstone.server.events.handler.EventHandler;
import com.shepherdjerred.capstone.server.events.handler.ThreadSafeEventQueue;
import com.shepherdjerred.capstone.server.events.network.ReceivedEventEvent;
import com.shepherdjerred.capstone.server.events.player.PlayerInfoEvent;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@AllArgsConstructor
public class ReceivedEventEventHandler implements EventHandler<ReceivedEventEvent> {

  private final ThreadSafeEventQueue queue;

  @Override
  public void handle(ReceivedEventEvent event) {
    var e = event.getEvent();
    if (shouldBeDispatched(e)) {
      queue.dispatchEvent(e);
    } else {
      log.warn("Received invalid event from client");
    }
  }

  private boolean shouldBeDispatched(Event event) {
    return event instanceof PlayerInfoEvent;
  }
}
