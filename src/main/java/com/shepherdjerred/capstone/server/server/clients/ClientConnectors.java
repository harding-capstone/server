package com.shepherdjerred.capstone.server.server.clients;

import com.shepherdjerred.capstone.server.event.Event;
import com.shepherdjerred.capstone.server.event.EventBus;
import com.shepherdjerred.capstone.server.packets.Packet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class ClientConnectors {

  private final Map<ClientHandle, ClientConnection> clientHandles;
  private final Set<ClientConnector> clientConnectors;
  private final EventBus<Event> eventBus;

  public ClientConnectors(EventBus<Event> serverEventBus) {
    this.clientHandles = new HashMap<>();
    this.clientConnectors = new HashSet<>();
    this.eventBus = serverEventBus;
  }

  public void sendPacket(ClientHandle clientHandle, Packet packet) {
    var connection = clientHandles.get(clientHandle);
    connection.sendPacket(packet);
  }

  public void addClientHandler(ClientHandle clientHandle, ClientConnection clientConnection) {
    clientHandles.put(clientHandle, clientConnection);
  }

  public void registerConnector(ClientConnector clientConnector) {
    clientConnectors.add(clientConnector);
    clientConnector.acceptConnections();
  }

  public void handleLatestEvents() {
    clientConnectors.forEach(clientConnector -> {
      if (clientConnector.hasEvent()) {
        var event = clientConnector.getNextEvent();
        eventBus.dispatch(event);
      }
    });
  }
}
