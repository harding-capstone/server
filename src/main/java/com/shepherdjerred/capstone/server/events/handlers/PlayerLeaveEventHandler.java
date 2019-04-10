package com.shepherdjerred.capstone.server.events.handlers;

import com.shepherdjerred.capstone.events.handlers.EventHandler;
import com.shepherdjerred.capstone.network.packet.packets.LobbyAction;
import com.shepherdjerred.capstone.network.packet.packets.PlayerLobbyActionPacket;
import com.shepherdjerred.capstone.server.GameServer;
import com.shepherdjerred.capstone.server.events.events.PlayerLeaveEvent;
import com.shepherdjerred.capstone.server.network.ClientId;
import com.shepherdjerred.capstone.server.network.ConnectorHub;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PlayerLeaveEventHandler implements EventHandler<PlayerLeaveEvent> {
  private final GameServer gameServer;
  private final ConnectorHub connectorHub;

  @Override
  public void handle(PlayerLeaveEvent playerLeaveEvent) {
    var clientId = new ClientId(playerLeaveEvent.getPlayer().getUuid());
    gameServer.removePlayer(clientId, playerLeaveEvent.getPlayer());
    connectorHub.sendPacket(new PlayerLobbyActionPacket(playerLeaveEvent.getPlayer(), LobbyAction.LEAVE) {
    });
  }

}
