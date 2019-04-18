package com.shepherdjerred.capstone.server.event.handlers;

import com.shepherdjerred.capstone.events.handlers.EventHandler;
import com.shepherdjerred.capstone.network.packet.packets.EditLobbyPacket;
import com.shepherdjerred.capstone.server.GameServer;
import com.shepherdjerred.capstone.server.event.events.EditLobbyEvent;
import com.shepherdjerred.capstone.server.network.ConnectorHub;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class EditLobbyEventHandler implements EventHandler<EditLobbyEvent> {
  private final GameServer gameServer;
  private final ConnectorHub connectorHub;

  @Override
  public void handle(EditLobbyEvent editLobbyEvent) {
    gameServer.updateLobbySettings(editLobbyEvent.getLobbySettings());
    connectorHub.sendPacket(new EditLobbyPacket(editLobbyEvent.getLobbySettings(), editLobbyEvent.getPlayer()) {
    });
  }
}
