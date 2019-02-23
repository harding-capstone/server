package com.shepherdjerred.capstone.server.server.handlers;

import com.shepherdjerred.capstone.server.events.client.ClientAcceptedEvent;
import com.shepherdjerred.capstone.server.events.handler.EventHandler;
import com.shepherdjerred.capstone.server.events.network.ConnectionAcceptedEvent;
import com.shepherdjerred.capstone.server.network.ClientManager;
import com.shepherdjerred.capstone.server.network.ClientStatus;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ClientAcceptedEventHandler implements EventHandler<ClientAcceptedEvent> {

  private final ClientManager clientManager;

  @Override
  public void handle(ClientAcceptedEvent event) {
    var client = event.getClient();
    client.setClientStatus(ClientStatus.CONNECTED);
    clientManager.addClient(client);

    var connectionAcceptedEvent = new ConnectionAcceptedEvent();
    client.sendEvent(connectionAcceptedEvent);
  }
}
