package com.shepherdjerred.capstone.server.server.events.handlers;

import com.shepherdjerred.capstone.server.event.handlers.EventHandler;
import com.shepherdjerred.capstone.server.packet.packets.ConnectionAcceptedPacket;
import com.shepherdjerred.capstone.server.server.clients.ClientConnectors;
import com.shepherdjerred.capstone.server.server.clients.events.ClientConnectedEvent;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ClientConnectedEventHandler implements EventHandler<ClientConnectedEvent> {

  private final ClientConnectors clientConnectors;

  @Override
  public void handle(ClientConnectedEvent event) {
    clientConnectors.addClientHandler(event.getClientHandle(), event.getClientConnection());
    clientConnectors.sendPacket(event.getClientHandle(), new ConnectionAcceptedPacket());
  }
}
