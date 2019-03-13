package com.shepherdjerred.capstone.server.events.handlers;

import com.shepherdjerred.capstone.events.handlers.EventHandler;
import com.shepherdjerred.capstone.server.GameServer;
import com.shepherdjerred.capstone.server.events.events.PlayerChatEvent;
import com.shepherdjerred.capstone.server.events.events.network.PacketReceivedEvent;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ClientPacketReceivedEventHandler implements EventHandler<PacketReceivedEvent> {

  private final GameServer gameServer;

  @Override
  public void handle(PacketReceivedEvent event) {
    var packet = event.getPacket();
    if (packet instanceof PlayerChatEvent) {
      gameServer.dispatch(new PlayerChatEvent(null, ((PlayerChatEvent) packet).getChatMessage()));
    }
  }
}
