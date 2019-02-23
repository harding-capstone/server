package com.shepherdjerred.capstone.server.network.local;

import com.shepherdjerred.capstone.server.events.Event;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import lombok.ToString;

@ToString
public class LocalBridge {
  private final Queue<Event> eventsToServer;
  private final Queue<Event> eventsToClient;

  public LocalBridge() {
    eventsToServer = new ConcurrentLinkedQueue<>();
    eventsToClient = new ConcurrentLinkedQueue<>();
  }

  public void publishToServer(Event event) {
    eventsToServer.add(event);
  }

  public void publishToClient(Event event) {
    eventsToClient.add(event);
  }

  public Event getEventForClient() {
    return eventsToClient.remove();
  }

  public Event getEventForServer() {
    return eventsToServer.remove();
  }

  public boolean hasEventForServer() {
    return !eventsToServer.isEmpty();
  }

  public boolean hasEventForClient() {
    return !eventsToClient.isEmpty();
  }

}
