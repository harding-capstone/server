package com.shepherdjerred.capstone.server.network.handlers;

import com.shepherdjerred.capstone.server.events.handler.EventHandler;
import com.shepherdjerred.capstone.server.network.connection.ConnectionStatus;
import com.shepherdjerred.capstone.server.network.NetworkManager;
import com.shepherdjerred.capstone.server.network.event.connection.ConnectionAcceptedEvent;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ConnectionAcceptedEventHandler implements EventHandler<ConnectionAcceptedEvent> {

  private final NetworkManager networkManager;

  @Override
  public void handle(ConnectionAcceptedEvent event) {
    var client = event.getConnection();
    client.setConnectionStatus(ConnectionStatus.CONNECTED);
    networkManager.addConnection(client);
    // TODO dispatch event to client
  }
}
