package com.shepherdjerred.capstone.server.event.handlers;

import com.shepherdjerred.capstone.events.handlers.EventHandler;
import com.shepherdjerred.capstone.network.packet.packets.CreatedPlayerPacket;
import com.shepherdjerred.capstone.network.packet.packets.LobbyAction;
import com.shepherdjerred.capstone.network.packet.packets.PlayerLobbyActionPacket;
import com.shepherdjerred.capstone.server.GameServer;
import com.shepherdjerred.capstone.server.event.events.PlayerJoinEvent;
import com.shepherdjerred.capstone.server.event.exception.LobbyFullException;
import com.shepherdjerred.capstone.server.network.ClientId;
import com.shepherdjerred.capstone.server.network.ConnectorHub;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PlayerJoinEventHandler implements EventHandler<PlayerJoinEvent> {

  private final GameServer gameServer;
  private final ConnectorHub connectorHub;

  @Override
  public void handle(PlayerJoinEvent playerJoinEvent) {
    try {
      var clientId = new ClientId(playerJoinEvent.getPlayer().getUuid());
      gameServer.addPlayer(clientId, playerJoinEvent.getPlayer());
      connectorHub.sendPacket(new PlayerLobbyActionPacket(playerJoinEvent.getPlayer(),
          LobbyAction.JOIN) {
      });
      connectorHub.sendPacket(clientId, new CreatedPlayerPacket(playerJoinEvent.getPlayer()));
    } catch (LobbyFullException e) {
      e.printStackTrace();
    }
  }
}