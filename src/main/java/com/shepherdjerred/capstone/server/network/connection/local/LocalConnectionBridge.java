package com.shepherdjerred.capstone.server.network.connection.local;

import com.shepherdjerred.capstone.server.network.packet.Packet;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import lombok.ToString;

@ToString
public class LocalConnectionBridge {
  private final Queue<Packet> eventsToServer;
  private final Queue<Packet> eventsToClient;

  public LocalConnectionBridge() {
    eventsToServer = new ConcurrentLinkedQueue<>();
    eventsToClient = new ConcurrentLinkedQueue<>();
  }

  public void publishToServer(Packet packet) {
    eventsToServer.add(packet);
  }

  public void publishToClient(Packet packet) {
    eventsToClient.add(packet);
  }

  public Packet getEventForClient() {
    return eventsToClient.remove();
  }

  public Packet getEventForServer() {
    return eventsToServer.remove();
  }

  public boolean hasEventForServer() {
    return !eventsToServer.isEmpty();
  }

  public boolean hasEventForClient() {
    return !eventsToClient.isEmpty();
  }

}
