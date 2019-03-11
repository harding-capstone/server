package com.shepherdjerred.capstone.server.server;

import com.shepherdjerred.capstone.server.event.Event;
import com.shepherdjerred.capstone.server.event.EventBus;
import com.shepherdjerred.capstone.server.event.handlers.EventLoggerHandler;
import com.shepherdjerred.capstone.server.server.clients.ClientConnector;
import com.shepherdjerred.capstone.server.server.clients.ClientConnectors;
import com.shepherdjerred.capstone.server.server.clients.events.ClientConnectedEvent;
import com.shepherdjerred.capstone.server.server.events.handlers.ClientConnectedEventHandler;
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
    registerConnectorEventHandlers();
  }

  public void registerConnectorEventHandlers() {
    eventQueue.registerHandler(new EventLoggerHandler<>());
    eventQueue.registerHandler(ClientConnectedEvent.class,
        new ClientConnectedEventHandler(clientConnectors));
  }

  public void run() throws InterruptedException {
    final int ticksPerSecond = 10;
    final int millisecondsPerSecond = 1000;
    final int sleepMilliseconds = millisecondsPerSecond / ticksPerSecond;

    while (true) {
      process();
      Thread.sleep(sleepMilliseconds);
    }
  }

  private void process() {
    clientConnectors.handleLatestEvents();
  }

  public void registerConnector(ClientConnector connector) {
    clientConnectors.registerConnector(connector);
  }
}
