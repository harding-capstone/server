package com.shepherdjerred.capstone.server;

import com.shepherdjerred.capstone.events.Event;
import com.shepherdjerred.capstone.events.EventBus;
import com.shepherdjerred.capstone.events.handlers.EventLoggerHandler;
import com.shepherdjerred.capstone.server.events.handlers.ClientConnectedEventHandler;
import com.shepherdjerred.capstone.server.network.ClientConnector;
import com.shepherdjerred.capstone.server.network.ClientConnectors;
import com.shepherdjerred.capstone.server.network.events.ClientConnectedEvent;
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
