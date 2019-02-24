package com.shepherdjerred.capstone.server.network;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.shepherdjerred.capstone.common.player.Player;
import com.shepherdjerred.capstone.server.events.handler.ThreadSafeEventQueue;
import com.shepherdjerred.capstone.server.events.network.ReceivedMessageEvent;
import com.shepherdjerred.capstone.server.network.event.connection.ConnectionAcceptedEvent;
import com.shepherdjerred.capstone.server.network.event.connection.ConnectionAttemptedEvent;
import com.shepherdjerred.capstone.server.network.handlers.ConnectionAcceptedEventHandler;
import com.shepherdjerred.capstone.server.network.local.LocalConnection;
import com.shepherdjerred.capstone.server.network.local.LocalConnectionBridge;
import java.util.HashSet;
import java.util.Set;

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

  /**
   * Takes messages from clients and puts them onto the event queue.
   */
  public void pullLatestMessages() {
    connections.forEach(connection -> {
      if (connection.hasMessage()) {
        var message = connection.getNextMessage();
        var player = playerConnectionMap.inverse().get(connection);
        var event = new ReceivedMessageEvent(player, message);
        eventQueue.dispatchEvent(event);
      }
    });
  }

  private void registerHandlers() {
    eventQueue.registerHandler(ConnectionAcceptedEvent.class, new ConnectionAcceptedEventHandler(this));
  }

  public void startLocalConnection(LocalConnectionBridge localConnectionBridge) {
    var client = new LocalConnection(localConnectionBridge);
    var event = new ConnectionAttemptedEvent(client);
    client.setConnectionStatus(ConnectionStatus.CONNECTING);
    eventQueue.dispatchEvent(event);
  }

  public void addConnection(Connection connection) {
    connections.add(connection);
  }
}
