package com.shepherdjerred.capstone.server.network.local;

import com.shepherdjerred.capstone.server.events.Event;
import com.shepherdjerred.capstone.server.network.Client;
import com.shepherdjerred.capstone.server.network.ClientStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Represents a connection from a server to a local client.
 */
@ToString
public class LocalClient implements Client {

  @Getter
  @Setter
  private ClientStatus clientStatus;
  private final LocalBridge localBridge;

  public LocalClient(LocalBridge localBridge) {
    this.localBridge = localBridge;
    this.clientStatus = ClientStatus.DISCONNECTED;
  }

  @Override
  public void sendEvent(Event event) {
    localBridge.publishToClient(event);
  }

  @Override
  public Event getIncomingEvent() {
    return localBridge.getEventForServer();
  }

  @Override
  public void disconnect() {

  }

  @Override
  public boolean hasEvent() {
    return localBridge.hasEventForServer();
  }
}
