package com.shepherdjerred.capstone.server.event.handlers;

import com.shepherdjerred.capstone.events.handlers.EventHandler;
import com.shepherdjerred.capstone.network.packet.packets.MatchBeginPacket;
import com.shepherdjerred.capstone.server.event.events.StartGameEvent;
import com.shepherdjerred.capstone.server.network.ConnectorHub;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class StartGameEventHandler implements EventHandler<StartGameEvent> {

  private final ConnectorHub connectorHub;

  @Override
  public void handle(StartGameEvent startGameEvent) {
    connectorHub.sendPacket(new MatchBeginPacket());
  }

}
