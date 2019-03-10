package com.shepherdjerred.capstone.server.server.clients;

import com.shepherdjerred.capstone.server.event.EventBus;
import com.shepherdjerred.capstone.server.server.clients.event.events.ClientConnectedEvent;
import com.shepherdjerred.capstone.server.server.clients.event.events.ConnectorEvent;
import com.shepherdjerred.capstone.server.server.clients.event.handlers.ClientConnectedEventHandler;
import com.shepherdjerred.capstone.server.event.Event;
import java.util.HashSet;
import java.util.Set;

public class ClientConnectors {

  private final Set<ClientConnector> clientConnectors;
  private final EventBus<Event> serverEventBus;
  private final EventBus<ConnectorEvent> connectorEventBus;

  public ClientConnectors(EventBus<Event> serverEventBus) {
    this.clientConnectors = new HashSet<>();
    this.serverEventBus = serverEventBus;
    this.connectorEventBus = new EventBus<>();
  }

  public void registerConnector(ClientConnector clientConnector) throws InterruptedException {
    clientConnectors.add(clientConnector);
    clientConnector.acceptConnections();
  }

  public void handleLatestEvents() {
    clientConnectors.forEach(clientConnector -> {
      if (clientConnector.hasEvent()) {
        connectorEventBus.dispatch(clientConnector.getLatestEvent());
      }
    });
  }

  public void dispatchServerEvent(Event event) {
    serverEventBus.dispatch(event);
  }

  public void dispatchConnectorEvent(ConnectorEvent event) {

  }

  private void registerEventHandlers() {
    connectorEventBus.registerHandler(ClientConnectedEvent.class,
        new ClientConnectedEventHandler(this));
  }
}
