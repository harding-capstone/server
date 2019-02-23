package com.shepherdjerred.capstone.server.network;


import com.shepherdjerred.capstone.server.events.Event;

public interface Client {

  void sendEvent(Event event);

  Event getIncomingEvent();

  void setClientStatus(ClientStatus clientStatus);

  ClientStatus getClientStatus();

  void disconnect();

  boolean hasEvent();

}
