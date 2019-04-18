package com.shepherdjerred.capstone.server.event.handlers;

import com.shepherdjerred.capstone.events.handlers.EventHandler;
import com.shepherdjerred.capstone.server.event.events.network.ClientDisconnectedEvent;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ClientDisconnectedEventHandler implements EventHandler<ClientDisconnectedEvent> {

  @Override
  public void handle(ClientDisconnectedEvent clientDisconnectedEvent) {
  }
}
