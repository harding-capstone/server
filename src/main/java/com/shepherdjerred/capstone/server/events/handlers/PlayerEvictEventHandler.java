package com.shepherdjerred.capstone.server.events.handlers;

import com.shepherdjerred.capstone.events.handlers.EventHandler;
import com.shepherdjerred.capstone.network.packet.packets.LobbyAction;
import com.shepherdjerred.capstone.network.packet.packets.PlayerLobbyActionPacket;
import com.shepherdjerred.capstone.server.GameServer;
import com.shepherdjerred.capstone.server.events.events.PlayerEvictEvent;
import com.shepherdjerred.capstone.server.network.ConnectorHub;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PlayerEvictEventHandler implements EventHandler<PlayerEvictEvent> {
  private final GameServer gameServer;
  private final ConnectorHub connectorHub;

  @Override
  public void handle(PlayerEvictEvent playerEvictEvent) {
    gameServer.removePlayer(playerEvictEvent.getClientId(), playerEvictEvent.getPlayer());
    connectorHub.sendPacket(new PlayerLobbyActionPacket(playerEvictEvent.getPlayer(), LobbyAction.EVICT) {
    });
  }

}
