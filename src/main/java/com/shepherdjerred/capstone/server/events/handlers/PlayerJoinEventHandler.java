package com.shepherdjerred.capstone.server.events.handlers;

import com.shepherdjerred.capstone.events.handlers.EventHandler;
import com.shepherdjerred.capstone.network.packet.packets.PlayerDescriptionPacket;
import com.shepherdjerred.capstone.network.packet.packets.PlayerJoinedPacket;
import com.shepherdjerred.capstone.server.GameServer;
import com.shepherdjerred.capstone.server.events.events.PlayerJoinEvent;
import com.shepherdjerred.capstone.server.network.ConnectorHub;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PlayerJoinEventHandler implements EventHandler<PlayerJoinEvent> {
  private final GameServer gameServer;
  private final ConnectorHub connectorHub;

  @Override
  public void handle(PlayerJoinEvent playerJoinEvent) {
    gameServer.addPlayer(playerJoinEvent.getClientId(), playerJoinEvent.getPlayer());
    connectorHub.sendPacket(new PlayerJoinedPacket(playerJoinEvent.getPlayer()) {
    });
  }
}
