package com.shepherdjerred.capstone.server.server;

import com.shepherdjerred.capstone.server.event.EventBus;
import com.shepherdjerred.capstone.server.server.clients.ClientConnector;
import com.shepherdjerred.capstone.server.server.clients.ClientConnectors;
import com.shepherdjerred.capstone.server.event.Event;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;

@Log4j2
@ToString
public class GameServer {

  private final ClientConnectors clientConnectors;
  private final EventBus<Event> eventQueue;

  public GameServer() {
    this.eventQueue = new EventBus<>();
    this.clientConnectors = new ClientConnectors(eventQueue);
  }

  public void run() throws InterruptedException {
    final int ticksPerSecond = 20;
    final int millisecondsPerSecond = 1000;
    final int sleepMilliseconds = millisecondsPerSecond / ticksPerSecond;

    while (true) {
      work();
      Thread.sleep(sleepMilliseconds);
    }
  }

  private void work() {
    clientConnectors.handleLatestEvents();
  }

  public void registerConnector(ClientConnector connector) throws InterruptedException {
    clientConnectors.registerConnector(connector);
  }
}
