package com.shepherdjerred.capstone.server.game;

import com.shepherdjerred.capstone.events.Event;
import com.shepherdjerred.capstone.events.EventBus;
import com.shepherdjerred.capstone.events.handlers.EventHandlerFrame;

public class LobbyServerState implements ServerState {

  private final EventBus<Event> eventBus;
  private final EventHandlerFrame<Event> eventHandlerFrame;

  public LobbyServerState(EventBus<Event> eventBus) {
    this.eventBus = eventBus;
    this.eventHandlerFrame = new EventHandlerFrame<>();
  }

  private void createEventHandlerFrame() {

  }

  @Override
  public void enable() {
    eventBus.registerHandlerFrame(eventHandlerFrame);
  }

  @Override
  public void disable() {
    eventBus.removeHandlerFrame(eventHandlerFrame);
  }
}
