package com.shepherdjerred.capstone.server.events.handlers;

import com.shepherdjerred.capstone.common.player.Element;
import com.shepherdjerred.capstone.common.player.HumanPlayer;
import com.shepherdjerred.capstone.common.player.Player;
import com.shepherdjerred.capstone.events.handlers.EventHandler;
import com.shepherdjerred.capstone.network.packet.packets.LobbyAction;
import com.shepherdjerred.capstone.network.packet.packets.PlayerDescriptionPacket;
import com.shepherdjerred.capstone.network.packet.packets.PlayerLobbyActionPacket;
import com.shepherdjerred.capstone.network.packet.packets.SendChatMessagePacket;
import com.shepherdjerred.capstone.server.GameServer;
import com.shepherdjerred.capstone.server.events.events.PlayerChatEvent;
import com.shepherdjerred.capstone.server.events.events.PlayerEvictEvent;
import com.shepherdjerred.capstone.server.events.events.PlayerJoinEvent;
import com.shepherdjerred.capstone.server.events.events.PlayerLeaveEvent;
import com.shepherdjerred.capstone.server.events.events.PlayerReconnectEvent;
import com.shepherdjerred.capstone.server.events.events.network.PacketReceivedEvent;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PacketReceivedEventHandler implements EventHandler<PacketReceivedEvent> {

  private final GameServer gameServer;

  @Override
  public void handle(PacketReceivedEvent event) {
    var packet = event.getPacket();
    if (packet instanceof SendChatMessagePacket) {
      gameServer.dispatch(new PlayerChatEvent(gameServer.getPlayerByClientId(event.getClientId()), ((SendChatMessagePacket) packet).getChatMessage()));
    } else if (packet instanceof PlayerDescriptionPacket) {
      //TODO add element to description
      Player player = new HumanPlayer(event.getClientId().getUuid(),
          ((PlayerDescriptionPacket) packet).getName(), Element.FIRE);
      gameServer.dispatch(new PlayerJoinEvent(event.getClientId(), player));
    } else if (packet instanceof PlayerLobbyActionPacket) {
      PlayerLobbyActionPacket lobbyActionPacket = (PlayerLobbyActionPacket) packet;
      handleLobbyActionPacket(event, lobbyActionPacket);
    }
  }

  private void handleLobbyActionPacket(PacketReceivedEvent event, PlayerLobbyActionPacket lobbyActionPacket) {
    if (lobbyActionPacket.getLobbyAction() == LobbyAction.JOIN) {
      gameServer.dispatch(new PlayerJoinEvent(event.getClientId(), lobbyActionPacket.getPlayer()));
    } else if (lobbyActionPacket.getLobbyAction() == LobbyAction.LEAVE) {
      gameServer.dispatch(new PlayerLeaveEvent(event.getClientId(), lobbyActionPacket.getPlayer()));
    } else if (lobbyActionPacket.getLobbyAction() == LobbyAction.EVICT) {
      gameServer.dispatch(new PlayerEvictEvent(event.getClientId(), lobbyActionPacket.getPlayer()));
    } else if (lobbyActionPacket.getLobbyAction() == LobbyAction.RECONNECT) {
      gameServer.dispatch(new PlayerReconnectEvent(event.getClientId(), lobbyActionPacket.getPlayer()));
    }
  }
}
