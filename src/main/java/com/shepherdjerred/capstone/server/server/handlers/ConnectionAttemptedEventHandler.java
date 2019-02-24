package com.shepherdjerred.capstone.server.server.handlers;

import com.shepherdjerred.capstone.server.events.handler.EventHandler;
import com.shepherdjerred.capstone.server.network.event.connection.ConnectionAcceptedEvent;
import com.shepherdjerred.capstone.server.network.event.connection.ConnectionAttemptedEvent;
import com.shepherdjerred.capstone.server.server.GameServer;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@AllArgsConstructor
public class ConnectionAttemptedEventHandler implements EventHandler<ConnectionAttemptedEvent> {

  private final GameServer gameServer;

  @Override
  public void handle(ConnectionAttemptedEvent event) {
    var client = event.getConnection();
    // TODO logic for declining connection connection
    // We'd want to stop a connection from connecting when the lobby is full or a game is in progress
    var acceptEvent = new ConnectionAcceptedEvent(client);
    gameServer.dispatchEvent(acceptEvent);
  }
}
