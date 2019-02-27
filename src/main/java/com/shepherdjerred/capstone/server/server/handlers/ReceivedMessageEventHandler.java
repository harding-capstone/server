package com.shepherdjerred.capstone.server.server.handlers;

import com.shepherdjerred.capstone.common.player.Element;
import com.shepherdjerred.capstone.common.player.HumanPlayer;
import com.shepherdjerred.capstone.common.player.Player;
import com.shepherdjerred.capstone.server.events.handler.EventHandler;
import com.shepherdjerred.capstone.server.events.player.PlayerJoinEvent;
import com.shepherdjerred.capstone.server.network.connection.Connection;
import com.shepherdjerred.capstone.server.network.event.network.ReceivedMessageEvent;
import com.shepherdjerred.capstone.server.network.packet.EventMessage;
import com.shepherdjerred.capstone.server.network.packet.PlayerInitializationPacket;
import com.shepherdjerred.capstone.server.server.GameServer;
import java.util.UUID;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ReceivedMessageEventHandler implements EventHandler<ReceivedMessageEvent> {

  private final GameServer server;

  @Override
  public void handle(ReceivedMessageEvent event) {
    var message = event.getPacket();
    var player = event.getPlayer();
    var connection = event.getConnection();

    if (message instanceof PlayerInitializationPacket) {
      handlePlayerInfoMessage((PlayerInitializationPacket) message, connection);
    } else if (message instanceof EventMessage) {
      handleEventMessage((EventMessage) message, player);
    }
  }

  private void handlePlayerInfoMessage(PlayerInitializationPacket message, Connection sender) {
    // TODO element should be removed
    var player = new HumanPlayer(UUID.randomUUID(), message.getPlayerName(), Element.FIRE);
    var event = new PlayerJoinEvent(player);
    server.dispatchEvent(event);
  }

  private void handleEventMessage(EventMessage message, Player sender) {
    var event = message.getEvent();
    // TODO here we should filter out event types that we shouldn't receive
  }
}
