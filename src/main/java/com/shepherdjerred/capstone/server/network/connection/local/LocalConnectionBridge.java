package com.shepherdjerred.capstone.server.network.connection.local;

import com.shepherdjerred.capstone.server.network.message.Message;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import lombok.ToString;

@ToString
public class LocalConnectionBridge {
  private final Queue<Message> eventsToServer;
  private final Queue<Message> eventsToClient;

  public LocalConnectionBridge() {
    eventsToServer = new ConcurrentLinkedQueue<>();
    eventsToClient = new ConcurrentLinkedQueue<>();
  }

  public void publishToServer(Message message) {
    eventsToServer.add(message);
  }

  public void publishToClient(Message message) {
    eventsToClient.add(message);
  }

  public Message getEventForClient() {
    return eventsToClient.remove();
  }

  public Message getEventForServer() {
    return eventsToServer.remove();
  }

  public boolean hasEventForServer() {
    return !eventsToServer.isEmpty();
  }

  public boolean hasEventForClient() {
    return !eventsToClient.isEmpty();
  }

}
