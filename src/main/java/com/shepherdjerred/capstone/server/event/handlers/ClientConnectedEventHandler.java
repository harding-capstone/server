package com.shepherdjerred.capstone.server.event.handlers;

import com.shepherdjerred.capstone.events.handlers.EventHandler;
import com.shepherdjerred.capstone.server.event.events.network.ClientConnectedEvent;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ClientConnectedEventHandler implements EventHandler<ClientConnectedEvent> {


  @Override
  public void handle(ClientConnectedEvent event) {
  }
}
