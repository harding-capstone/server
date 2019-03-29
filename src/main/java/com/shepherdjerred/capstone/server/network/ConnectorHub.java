package com.shepherdjerred.capstone.server.network;

import com.shepherdjerred.capstone.events.Event;
import com.shepherdjerred.capstone.events.EventBus;
import com.shepherdjerred.capstone.network.packet.packets.Packet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import lombok.extern.log4j.Log4j2;

/**
 * Composes multiple connectors.
 */
@Log4j2
public class ConnectorHub {

  private final Map<ClientId, Connection> connectionHandles;
  private final Set<Connector> connectors;
  private final EventBus<Event> eventBus;

  public ConnectorHub(EventBus<Event> serverEventBus) {
    this.connectionHandles = new HashMap<>();
    this.connectors = new HashSet<>();
    this.eventBus = serverEventBus;
  }

  public void sendPacket(ClientId clientId, Packet packet) {
    var connection = connectionHandles.get(clientId);
    connection.sendPacket(packet);
  }

  public void sendPacket(Packet packet) {
    connectionHandles.values().forEach(connection -> {
      connection.sendPacket(packet);
    });
  }

  public void addClientHandle(ClientId clientId, Connection connection) {
    connectionHandles.put(clientId, connection);
  }

  public void registerConnector(Connector connector) {
    connectors.add(connector);
    connector.acceptConnections();
  }

  public void handleLatestEvents() {
    connectors.forEach(connector -> {
      if (connector.hasEvent()) {
        var event = connector.getNextEvent();
        eventBus.dispatch(event);
      }
    });
  }
}