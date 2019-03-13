package com.shepherdjerred.capstone.server.events.handlers;

import com.shepherdjerred.capstone.events.handlers.EventHandler;
import com.shepherdjerred.capstone.network.packet.packets.UpdateChatHistoryPacket;
import com.shepherdjerred.capstone.server.GameServer;
import com.shepherdjerred.capstone.server.events.events.PlayerChatEvent;
import com.shepherdjerred.capstone.server.network.ConnectorHub;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PlayerChatEventHandler implements EventHandler<PlayerChatEvent> {

  private final GameServer gameServer;
  private final ConnectorHub connectorHub;

  @Override
  public void handle(PlayerChatEvent playerChatEvent) {
    gameServer.addChatMessage(playerChatEvent.getChatMessage());
    connectorHub.sendPacket(new UpdateChatHistoryPacket(gameServer.getChatHistory()));
  }
}
