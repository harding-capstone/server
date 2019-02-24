package com.shepherdjerred.capstone.server.server.handlers;

import com.shepherdjerred.capstone.server.events.handler.EventHandler;
import com.shepherdjerred.capstone.server.events.lobby.LobbyUpdatedEvent;
import com.shepherdjerred.capstone.server.events.player.PlayerJoinEvent;
import com.shepherdjerred.capstone.server.server.GameServer;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PlayerJoinEventHandler implements EventHandler<PlayerJoinEvent> {

  private final GameServer gameServer;

  @Override
  public void handle(PlayerJoinEvent event) {
    var player = event.getPlayer();
    gameServer.addPlayer(player);
    gameServer.dispatchEvent(new LobbyUpdatedEvent());
  }
}
