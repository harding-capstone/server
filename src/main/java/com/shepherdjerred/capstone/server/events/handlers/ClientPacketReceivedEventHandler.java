package com.shepherdjerred.capstone.server.events.handlers;

import com.shepherdjerred.capstone.common.player.Element;
import com.shepherdjerred.capstone.common.player.HumanPlayer;
import com.shepherdjerred.capstone.common.player.Player;
import com.shepherdjerred.capstone.events.handlers.EventHandler;
import com.shepherdjerred.capstone.network.packet.packets.PlayerDescriptionPacket;
import com.shepherdjerred.capstone.network.packet.packets.SendChatMessagePacket;
import com.shepherdjerred.capstone.server.GameServer;
import com.shepherdjerred.capstone.server.events.events.PlayerChatEvent;
import com.shepherdjerred.capstone.server.events.events.PlayerJoinEvent;
import com.shepherdjerred.capstone.server.events.events.network.PacketReceivedEvent;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ClientPacketReceivedEventHandler implements EventHandler<PacketReceivedEvent> {

  private final GameServer gameServer;

  @Override
  public void handle(PacketReceivedEvent event) {
    var packet = event.getPacket();
    if (packet instanceof SendChatMessagePacket) {
      gameServer.dispatch(new PlayerChatEvent(gameServer.getPlayerByHandle(event.getHandle()), ((SendChatMessagePacket) packet).getChatMessage()));
    } else if (packet instanceof PlayerDescriptionPacket) {
      //TODO add element to description
      Player player = new HumanPlayer(event.getHandle().getUuid(), ((PlayerDescriptionPacket) packet).getName(), Element.FIRE);
      gameServer.dispatch(new PlayerJoinEvent(event.getHandle(), player));
    }
  }
}
