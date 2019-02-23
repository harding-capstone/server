package com.shepherdjerred.capstone.server.server.handlers;

import com.shepherdjerred.capstone.common.player.Element;
import com.shepherdjerred.capstone.common.player.HumanPlayer;
import com.shepherdjerred.capstone.server.events.handler.EventHandler;
import com.shepherdjerred.capstone.server.events.player.PlayerInfoEvent;
import com.shepherdjerred.capstone.server.events.player.PlayerJoinEvent;
import com.shepherdjerred.capstone.server.server.Server;
import java.util.UUID;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PlayerInfoEventHandler implements EventHandler<PlayerInfoEvent> {

  private final Server server;

  @Override
  public void handle(PlayerInfoEvent event) {
    var playerUuid = UUID.randomUUID();
    var playerName = event.getPlayerName();
    // TODO element shouldn't be here
    var player = new HumanPlayer(playerUuid, playerName, Element.FIRE);
    var playerJoinEvent = new PlayerJoinEvent(player);
    server.dispatchEvent(playerJoinEvent);
  }
}
