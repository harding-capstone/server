package com.shepherdjerred.capstone.server.game;

import com.shepherdjerred.capstone.events.Event;
import com.shepherdjerred.capstone.events.EventBus;
import com.shepherdjerred.capstone.events.handlers.EventHandlerFrame;
import com.shepherdjerred.capstone.network.packet.packets.PlayerJoinPacket;
import com.shepherdjerred.capstone.server.GameServer;
import com.shepherdjerred.capstone.server.event.events.network.PlayerJoinEvent;

public class LobbyServerState implements ServerState {

  private final GameServer gameServer;
  private final EventBus<Event> eventBus;
  private final EventHandlerFrame<Event> eventHandlerFrame;

  public LobbyServerState(GameServer gameServer, EventBus<Event> eventBus) {
    this.gameServer = gameServer;
    this.eventBus = eventBus;
    this.eventHandlerFrame = new EventHandlerFrame<>();
    createEventHandlerFrame();
  }

  private void createEventHandlerFrame() {
    eventHandlerFrame.registerHandler(PlayerJoinEvent.class, event -> gameServer.send(new PlayerJoinPacket(event.getPlayer())));
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
