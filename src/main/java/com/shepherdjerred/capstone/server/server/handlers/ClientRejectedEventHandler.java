package com.shepherdjerred.capstone.server.server.handlers;

import com.shepherdjerred.capstone.server.events.client.ClientRejectedEvent;
import com.shepherdjerred.capstone.server.events.handler.EventHandler;
import com.shepherdjerred.capstone.server.network.ClientManager;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ClientRejectedEventHandler implements EventHandler<ClientRejectedEvent> {

  private final ClientManager clientManager;

  @Override
  public void handle(ClientRejectedEvent event) {
    // TODO send a event with the disconnection reason
    event.getClient().disconnect();
  }
}
