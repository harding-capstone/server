package com.shepherdjerred.capstone.server.network;

import com.shepherdjerred.capstone.server.events.events.network.NetworkEvent;

public interface Connector {

  void acceptConnections();

  NetworkEvent getNextEvent();

  boolean hasEvent();
}
