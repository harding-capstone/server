package com.shepherdjerred.capstone.server.network.handlers;

import com.shepherdjerred.capstone.server.events.handler.EventHandler;
import com.shepherdjerred.capstone.server.events.player.PlayerJoinEvent;
import com.shepherdjerred.capstone.server.network.NetworkManager;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PlayerJoinEventHandler implements EventHandler<PlayerJoinEvent> {

  private final NetworkManager networkManager;

  @Override
  public void handle(PlayerJoinEvent event) {

  }
}
