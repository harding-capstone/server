package com.shepherdjerred.capstone.server.network;

import com.shepherdjerred.capstone.server.events.Event;
import com.shepherdjerred.capstone.server.events.client.ClientConnectedEvent;
import com.shepherdjerred.capstone.server.events.handler.ThreadSafeEventQueue;
import com.shepherdjerred.capstone.server.events.network.ReceivedEventEvent;
import com.shepherdjerred.capstone.server.network.local.LocalBridge;
import com.shepherdjerred.capstone.server.network.local.LocalClient;
import java.util.HashSet;
import java.util.Set;

public class ClientManager {

  private final ThreadSafeEventQueue eventQueue;
  private final Set<Client> clients;

  public ClientManager(ThreadSafeEventQueue eventQueue) {
    clients = new HashSet<>();
    this.eventQueue = eventQueue;
    registerHandlers();
  }

  public void pullLatestEvents() {
    clients.forEach(client -> {
      if (client.hasEvent()) {
        // TODO some kind of verification would be good to have
        var event = client.getIncomingEvent();
        var recvEvent = new ReceivedEventEvent(client, event);
        eventQueue.dispatchEvent(recvEvent);
      }
    });
  }

  public void dispatchEvent(Event event) {
    eventQueue.dispatchEvent(event);
  }

  private void registerHandlers() {

  }

  public void startLocalConnection(LocalBridge localBridge) {
    var client = new LocalClient(localBridge);
    var event = new ClientConnectedEvent(client);
    client.setClientStatus(ClientStatus.CONNECTING);
    eventQueue.dispatchEvent(event);
  }

  public void addClient(Client client) {
    clients.add(client);
  }
}
