package com.shepherdjerred.capstone.server.event.handlers;

import com.shepherdjerred.capstone.events.handlers.EventHandler;
import com.shepherdjerred.capstone.logic.match.MatchStatus.Status;
import com.shepherdjerred.capstone.network.packet.packets.GameOverPacket;
import com.shepherdjerred.capstone.network.packet.packets.LobbyAction;
import com.shepherdjerred.capstone.network.packet.packets.PlayerLobbyActionPacket;
import com.shepherdjerred.capstone.server.GameServer;
import com.shepherdjerred.capstone.server.event.events.PlayerLeaveEvent;
import com.shepherdjerred.capstone.server.network.ConnectorHub;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PlayerLeaveEventHandler implements EventHandler<PlayerLeaveEvent> {

  private final GameServer gameServer;
  private final ConnectorHub connectorHub;

  @Override
  public void handle(PlayerLeaveEvent playerLeaveEvent) {
    if (gameServer.hasMatchStarted()) {
      connectorHub.sendPacket(new GameOverPacket(Status.STALEMATE));
    } else {
      gameServer.removePlayer(playerLeaveEvent.getPlayer());
      connectorHub.sendPacket(new PlayerLobbyActionPacket(playerLeaveEvent.getPlayer(),
          LobbyAction.LEAVE));
    }
  }

}
