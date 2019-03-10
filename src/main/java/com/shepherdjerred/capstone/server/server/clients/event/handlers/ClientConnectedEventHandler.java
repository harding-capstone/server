package com.shepherdjerred.capstone.server.server.clients.event.handlers;

import com.shepherdjerred.capstone.server.event.handlers.EventHandler;
import com.shepherdjerred.capstone.server.server.clients.ClientConnectors;
import com.shepherdjerred.capstone.server.server.clients.event.events.ClientConnectedEvent;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ClientConnectedEventHandler implements EventHandler<ClientConnectedEvent> {

  private final ClientConnectors clientConnectors;

  @Override
  public void handle(ClientConnectedEvent event) {

  }
}
