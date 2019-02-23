package com.shepherdjerred.capstone.server.server.handlers;

import com.shepherdjerred.capstone.server.events.handler.EventHandler;
import com.shepherdjerred.capstone.server.events.lobby.LobbyUpdatedEvent;
import com.shepherdjerred.capstone.server.events.player.PlayerJoinEvent;
import com.shepherdjerred.capstone.server.server.Server;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PlayerJoinEventHandler implements EventHandler<PlayerJoinEvent> {

  private final Server server;

  @Override
  public void handle(PlayerJoinEvent event) {
    var player = event.getPlayer();
    server.addPlayer(player);
    server.dispatchEvent(new LobbyUpdatedEvent());
  }
}
