package com.shepherdjerred.capstone.server.events.handlers;

import com.shepherdjerred.capstone.events.handlers.EventHandler;
import com.shepherdjerred.capstone.network.packet.packets.EditLobbyPacket;
import com.shepherdjerred.capstone.server.GameServer;
import com.shepherdjerred.capstone.server.events.events.EditLobbyEvent;
import com.shepherdjerred.capstone.server.events.exception.LobbyFullException;
import com.shepherdjerred.capstone.server.network.ClientId;
import com.shepherdjerred.capstone.server.network.ConnectorHub;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class EditLobbyEventHandler implements EventHandler<EditLobbyEvent> {
  private final GameServer gameServer;
  private final ConnectorHub connectorHub;

  @Override
  public void handle(EditLobbyEvent editLobbyEvent) {
    var clientId = new ClientId(editLobbyEvent.getPlayer().getUuid());
    gameServer.updateLobby(editLobbyEvent.getLobbySettings());

    try {
      gameServer.addPlayer(clientId, editLobbyEvent.getPlayer());
    } catch (LobbyFullException e) {
      e.printStackTrace();
    }

    connectorHub.sendPacket(new EditLobbyPacket(editLobbyEvent.getLobbySettings(), editLobbyEvent.getPlayer()) {
    });
  }
}
