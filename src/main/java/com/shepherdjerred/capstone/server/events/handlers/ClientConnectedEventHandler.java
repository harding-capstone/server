package com.shepherdjerred.capstone.server.events.handlers;

import com.shepherdjerred.capstone.events.handlers.EventHandler;
import com.shepherdjerred.capstone.network.packet.packets.ConnectionAcceptedPacket;
import com.shepherdjerred.capstone.server.network.ClientConnectors;
import com.shepherdjerred.capstone.server.network.events.ClientConnectedEvent;
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
