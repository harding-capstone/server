package com.shepherdjerred.capstone.server.events.handlers;

import com.shepherdjerred.capstone.events.handlers.EventHandler;
import com.shepherdjerred.capstone.network.packet.packets.ConnectionAcceptedPacket;
import com.shepherdjerred.capstone.server.network.ConnectorHub;
import com.shepherdjerred.capstone.server.events.events.network.ClientConnectedEvent;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ClientConnectedEventHandler implements EventHandler<ClientConnectedEvent> {

  private final ConnectorHub connectorHub;

  @Override
  public void handle(ClientConnectedEvent event) {
    connectorHub.addClientHandle(event.getClientId(), event.getConnection());
    connectorHub.sendPacket(event.getClientId(), new ConnectionAcceptedPacket());
  }
}
