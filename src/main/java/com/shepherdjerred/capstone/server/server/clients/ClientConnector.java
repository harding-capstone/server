package com.shepherdjerred.capstone.server.server.clients;

import com.shepherdjerred.capstone.server.server.clients.events.ConnectorEvent;

public interface ClientConnector {

  void acceptConnections();

  ConnectorEvent getNextEvent();

  boolean hasEvent();
}
