package com.shepherdjerred.capstone.server.events.handlers;

import com.shepherdjerred.capstone.events.handlers.EventHandler;
import com.shepherdjerred.capstone.logic.match.MatchStatus.Status;
import com.shepherdjerred.capstone.network.packet.packets.GameOverPacket;
import com.shepherdjerred.capstone.network.packet.packets.LobbyAction;
import com.shepherdjerred.capstone.network.packet.packets.PlayerLobbyActionPacket;
import com.shepherdjerred.capstone.server.GameServer;
import com.shepherdjerred.capstone.server.events.events.network.ClientDisconnectedEvent;
import com.shepherdjerred.capstone.server.network.ConnectorHub;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ClientDisconnectedEventHandler implements EventHandler<ClientDisconnectedEvent> {

  private final GameServer gameServer;
  private final ConnectorHub connectorHub;

  @Override
  public void handle(ClientDisconnectedEvent clientDisconnectedEvent) {
    if (gameServer.getMatch() == null) {
      gameServer.removePlayer(clientDisconnectedEvent.getClientId());
      connectorHub.sendPacket(new PlayerLobbyActionPacket(gameServer.getPlayerByClientId(clientDisconnectedEvent.getClientId()), LobbyAction.LEAVE) {
      });
    } else {
      connectorHub.sendPacket(new GameOverPacket(Status.STALEMATE));
    }
  }
}
