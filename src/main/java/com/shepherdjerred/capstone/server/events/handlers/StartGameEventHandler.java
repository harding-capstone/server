package com.shepherdjerred.capstone.server.events.handlers;

import com.shepherdjerred.capstone.events.handlers.EventHandler;
import com.shepherdjerred.capstone.network.packet.packets.StartGamePacket;
import com.shepherdjerred.capstone.server.GameServer;
import com.shepherdjerred.capstone.server.events.events.StartGameEvent;
import com.shepherdjerred.capstone.server.network.ConnectorHub;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class StartGameEventHandler implements EventHandler<StartGameEvent> {
  private final GameServer gameServer;
  private final ConnectorHub connectorHub;

  @Override
  public void handle(StartGameEvent startGameEvent) {
    gameServer.setMatch(startGameEvent.getMatch());
    connectorHub.sendPacket(new StartGamePacket(startGameEvent.getMatch()) {
    });
  }

}
