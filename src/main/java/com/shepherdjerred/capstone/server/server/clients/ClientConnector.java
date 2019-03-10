package com.shepherdjerred.capstone.server.server.clients;

import com.shepherdjerred.capstone.server.server.clients.event.events.ConnectorEvent;

public interface ClientConnector {

  void acceptConnections() throws InterruptedException;

  ConnectorEvent getLatestEvent();

  boolean hasEvent();
}
