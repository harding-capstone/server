package com.shepherdjerred.capstone.server.server.events.handlers;

import com.shepherdjerred.capstone.server.event.handlers.EventHandler;
import com.shepherdjerred.capstone.server.server.clients.ClientConnectors;
import com.shepherdjerred.capstone.server.server.clients.events.ClientPacketReceivedEvent;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ClientPacketReceivedEventHandler implements EventHandler<ClientPacketReceivedEvent> {

  private final ClientConnectors clientConnectors;

  @Override
  public void handle(ClientPacketReceivedEvent event) {

  }
}
