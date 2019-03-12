package com.shepherdjerred.capstone.server.network;

import com.shepherdjerred.capstone.server.network.events.NetworkEvent;

public interface ClientConnector {

  void acceptConnections();

  NetworkEvent getNextEvent();

  boolean hasEvent();
}
