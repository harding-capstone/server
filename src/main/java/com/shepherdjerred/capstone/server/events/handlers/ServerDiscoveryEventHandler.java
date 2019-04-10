package com.shepherdjerred.capstone.server.events.handlers;

import com.shepherdjerred.capstone.events.handlers.EventHandler;
import com.shepherdjerred.capstone.network.packet.packets.ServerDiscoveryPacket;
import com.shepherdjerred.capstone.server.GameServer;
import com.shepherdjerred.capstone.server.events.events.ServerDiscoveryEvent;
import com.shepherdjerred.capstone.server.network.ConnectorHub;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ServerDiscoveryEventHandler implements EventHandler<ServerDiscoveryEvent> {

  private final GameServer gameServer;
  private final ConnectorHub connectorHub;

  @Override
  public void handle(ServerDiscoveryEvent serverDiscoveryEvent) {
    connectorHub.sendPacket(new ServerDiscoveryPacket() {
    });
  }
}
