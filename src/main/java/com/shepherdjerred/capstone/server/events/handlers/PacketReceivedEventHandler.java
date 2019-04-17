package com.shepherdjerred.capstone.server.events.handlers;

import com.shepherdjerred.capstone.common.lobby.LobbySettings;
import com.shepherdjerred.capstone.common.player.Element;
import com.shepherdjerred.capstone.common.player.HumanPlayer;
import com.shepherdjerred.capstone.common.player.Player;
import com.shepherdjerred.capstone.events.handlers.EventHandler;
import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.network.packet.packets.EditLobbyPacket;
import com.shepherdjerred.capstone.network.packet.packets.LobbyAction;
import com.shepherdjerred.capstone.network.packet.packets.MakeTurnPacket;
import com.shepherdjerred.capstone.network.packet.packets.PlayerDescriptionPacket;
import com.shepherdjerred.capstone.network.packet.packets.PlayerLobbyActionPacket;
import com.shepherdjerred.capstone.network.packet.packets.ReadyToStartGamePacket;
import com.shepherdjerred.capstone.network.packet.packets.SendChatMessagePacket;
import com.shepherdjerred.capstone.server.GameServer;
import com.shepherdjerred.capstone.server.events.events.EditLobbyEvent;
import com.shepherdjerred.capstone.server.events.events.HostLeaveEvent;
import com.shepherdjerred.capstone.server.events.events.PlayerChatEvent;
import com.shepherdjerred.capstone.server.events.events.PlayerEvictEvent;
import com.shepherdjerred.capstone.server.events.events.PlayerJoinEvent;
import com.shepherdjerred.capstone.server.events.events.PlayerLeaveEvent;
import com.shepherdjerred.capstone.server.events.events.StartGameEvent;
import com.shepherdjerred.capstone.server.events.events.TurnEvent;
import com.shepherdjerred.capstone.server.events.events.network.PacketReceivedEvent;
import com.shepherdjerred.capstone.server.network.ClientId;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PacketReceivedEventHandler implements EventHandler<PacketReceivedEvent> {

  private final GameServer gameServer;

  @Override
  public void handle(PacketReceivedEvent event) {
    var packet = event.getPacket();
    if (packet instanceof SendChatMessagePacket) {
      handleSendChatMessagePacket(event.getClientId(), (SendChatMessagePacket) packet);
    } else if (packet instanceof PlayerDescriptionPacket) {
      handlePlayerDescriptionPacket(event.getClientId(), (PlayerDescriptionPacket) packet);
    } else if (packet instanceof PlayerLobbyActionPacket) {
      handleLobbyActionPacket((PlayerLobbyActionPacket) packet);
    } else if (packet instanceof EditLobbyPacket) {
      handleEditLobbyPacket((EditLobbyPacket) packet);
    } else if (packet instanceof ReadyToStartGamePacket) {
      handleReadyToStartGamePacket((ReadyToStartGamePacket) packet);
    } else if (packet instanceof MakeTurnPacket) {
      handleMakeTurnPacket((MakeTurnPacket) packet);
    }
  }

  private void handleSendChatMessagePacket(ClientId clientId, SendChatMessagePacket sendChatMessagePacket) {
    gameServer.dispatch(new PlayerChatEvent(gameServer.getPlayerByClientId(clientId), sendChatMessagePacket.getChatMessage()));
  }

  private void handlePlayerDescriptionPacket(ClientId clientId ,PlayerDescriptionPacket playerDescriptionPacket) {
    Element element = gameServer.getNextElement();

    if (element != null) {
      Player player = new HumanPlayer(clientId.getUuid(),
          playerDescriptionPacket.getName(), element);
      gameServer.dispatch(new PlayerJoinEvent(player));
    }
  }

  private void handleLobbyActionPacket(PlayerLobbyActionPacket lobbyActionPacket) {
    var player = lobbyActionPacket.getPlayer();
    if (lobbyActionPacket.getLobbyAction() == LobbyAction.JOIN) {
      gameServer.dispatch(new PlayerJoinEvent(player));
    } else if (lobbyActionPacket.getLobbyAction() == LobbyAction.LEAVE) {
      gameServer.dispatch(new PlayerLeaveEvent(player));
    } else if (lobbyActionPacket.getLobbyAction() == LobbyAction.EVICT) {
      gameServer.dispatch(new PlayerEvictEvent(player));
    } else if (lobbyActionPacket.getLobbyAction() == LobbyAction.HOSTLEAVE) {
      gameServer.dispatch(new HostLeaveEvent(player));
    }
  }

  private void handleEditLobbyPacket(EditLobbyPacket editLobbyPacket) {
    gameServer.dispatch(new EditLobbyEvent(editLobbyPacket.getLobbySettings(), editLobbyPacket.getPlayer()));
  }

  private void handleReadyToStartGamePacket(ReadyToStartGamePacket readyToStartGamePacket) {
    if (readyToStartGamePacket.isPlayersReady()) {
      LobbySettings lobbySettings = gameServer.getLobby().getLobbySettings();
      Match match = Match.from(lobbySettings.getMatchSettings(),
          lobbySettings.getBoardSettings());
      gameServer.dispatch(new StartGameEvent(match));
    }
  }

  private void handleMakeTurnPacket(MakeTurnPacket makeTurnPacket) {
    gameServer.dispatch(new TurnEvent(makeTurnPacket.getTurn()));
  }

}
