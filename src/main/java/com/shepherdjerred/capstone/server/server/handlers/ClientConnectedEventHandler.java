package com.shepherdjerred.capstone.server.server.handlers;

import com.shepherdjerred.capstone.server.events.handler.EventHandler;
import com.shepherdjerred.capstone.server.server.Server;
import com.shepherdjerred.capstone.server.events.client.ClientAcceptedEvent;
import com.shepherdjerred.capstone.server.events.client.ClientConnectedEvent;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@AllArgsConstructor
public class ClientConnectedEventHandler implements EventHandler<ClientConnectedEvent> {

  private final Server server;

  @Override
  public void handle(ClientConnectedEvent event) {
    var client = event.getClient();
    // TODO logic for declining client connection
    // We'd want to stop a client from connecting when the lobby is full or a game is in progress
    var acceptEvent = new ClientAcceptedEvent(client);
    server.dispatchEvent(acceptEvent);
  }
}
