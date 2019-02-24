package com.shepherdjerred.capstone.server.network;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.shepherdjerred.capstone.common.player.Player;
import com.shepherdjerred.capstone.server.events.handler.ThreadSafeEventQueue;
import com.shepherdjerred.capstone.server.network.connection.Connection;
import com.shepherdjerred.capstone.server.network.connection.ConnectionStatus;
import com.shepherdjerred.capstone.server.network.event.network.ReceivedMessageEvent;
import com.shepherdjerred.capstone.server.network.event.connection.ConnectionAcceptedEvent;
import com.shepherdjerred.capstone.server.network.event.connection.ConnectionAttemptedEvent;
import com.shepherdjerred.capstone.server.network.handlers.ConnectionAcceptedEventHandler;
import com.shepherdjerred.capstone.server.network.connection.local.LocalConnection;
import com.shepherdjerred.capstone.server.network.connection.local.LocalConnectionBridge;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class NetworkManager {

  private final ThreadSafeEventQueue eventQueue;
  private final Set<Connection> connections;
  private final BiMap<Player, Connection> playerConnectionMap;

  public NetworkManager(ThreadSafeEventQueue eventQueue) {
    connections = new HashSet<>();
    this.eventQueue = eventQueue;
    this.playerConnectionMap = HashBiMap.create();
    registerHandlers();
  }

  public void run() throws InterruptedException {
    final int ticksPerSecond = 20;
    final int millisecondsPerSecond = 1000;
    final int sleepMilliseconds = millisecondsPerSecond / ticksPerSecond;

    while (true) {
      pullLatestMessages();
      Thread.sleep(sleepMilliseconds);
    }
  }

  /**
   * Takes latest message from each client and puts them onto the event queue.
   */
  public void pullLatestMessages() {
    connections.forEach(connection -> {
      if (connection.getConnectionStatus() == ConnectionStatus.CONNECTED
          && connection.hasMessage()) {
        var message = connection.getNextMessage();
        var player = playerConnectionMap.inverse().get(connection);
        var event = new ReceivedMessageEvent(connection, player, message);
        eventQueue.dispatchEvent(event);
      }
    });
  }

  private void registerHandlers() {
    eventQueue.registerHandler(ConnectionAcceptedEvent.class,
        new ConnectionAcceptedEventHandler(this));
  }

  public void startLocalConnection(LocalConnectionBridge localConnectionBridge) {
    var connection = new LocalConnection(UUID.randomUUID(), localConnectionBridge);
    var event = new ConnectionAttemptedEvent(connection);
    connection.setConnectionStatus(ConnectionStatus.CONNECTING);
    eventQueue.dispatchEvent(event);
  }

  public void mapConnection(Connection connection, Player player) {
    playerConnectionMap.put(player, connection);
  }

  public void addConnection(Connection connection) {
    connections.add(connection);
  }
}
