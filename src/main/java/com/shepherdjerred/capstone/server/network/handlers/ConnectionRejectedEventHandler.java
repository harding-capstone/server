package com.shepherdjerred.capstone.server.network.handlers;

import com.shepherdjerred.capstone.server.network.event.connection.ConnectionRejectedEvent;
import com.shepherdjerred.capstone.server.events.handler.EventHandler;
import com.shepherdjerred.capstone.server.network.NetworkManager;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ConnectionRejectedEventHandler implements EventHandler<ConnectionRejectedEvent> {

  private final NetworkManager networkManager;

  @Override
  public void handle(ConnectionRejectedEvent event) {
    // TODO send a event with the disconnection reason
    event.getConnection().disconnect();
  }
}
