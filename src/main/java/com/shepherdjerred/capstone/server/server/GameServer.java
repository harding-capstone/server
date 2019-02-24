package com.shepherdjerred.capstone.server.server;

import com.shepherdjerred.capstone.common.chat.ChatHistory;
import com.shepherdjerred.capstone.common.lobby.Lobby;
import com.shepherdjerred.capstone.common.player.Player;
import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.player.PlayerId;
import com.shepherdjerred.capstone.server.events.Event;
import com.shepherdjerred.capstone.server.events.handler.EventLoggerHandler;
import com.shepherdjerred.capstone.server.events.handler.ThreadSafeEventQueue;
import com.shepherdjerred.capstone.server.network.event.network.ReceivedMessageEvent;
import com.shepherdjerred.capstone.server.events.player.PlayerJoinEvent;
import com.shepherdjerred.capstone.server.network.NetworkManager;
import com.shepherdjerred.capstone.server.network.event.connection.ConnectionAttemptedEvent;
import com.shepherdjerred.capstone.server.network.connection.local.LocalConnectionBridge;
import com.shepherdjerred.capstone.server.server.handlers.ConnectionAttemptedEventHandler;
import com.shepherdjerred.capstone.server.server.handlers.PlayerJoinEventHandler;
import com.shepherdjerred.capstone.server.server.handlers.ReceivedMessageEventHandler;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;

@Log4j2
@ToString
public class GameServer {

  private final NetworkManager networkManager;
  private final ServerSettings serverSettings;
  private final ThreadSafeEventQueue eventQueue;
  private ChatHistory chatHistory;
  private Match match;
  private Lobby lobby;

  public GameServer(ServerSettings serverSettings,
      ChatHistory chatHistory,
      Match match,
      Lobby lobby) {
    this.serverSettings = serverSettings;
    this.chatHistory = chatHistory;
    this.match = match;
    this.lobby = lobby;
    this.eventQueue = new ThreadSafeEventQueue();
    this.networkManager = new NetworkManager(eventQueue);
    registerHandlers();
  }

  private void registerHandlers() {
    eventQueue.registerHandler(Event.class, new EventLoggerHandler());
    eventQueue.registerHandler(ConnectionAttemptedEvent.class,
        new ConnectionAttemptedEventHandler(this));
    eventQueue.registerHandler(PlayerJoinEvent.class, new PlayerJoinEventHandler(this));
    eventQueue.registerHandler(ReceivedMessageEvent.class, new ReceivedMessageEventHandler(this));
  }

  public void addPlayer(Player player) {
    // TODO get next open player ID somehow instead of hardcoding
    lobby.addPlayer(PlayerId.ONE, player);
  }

  public void run() throws InterruptedException {
    new Thread(() -> {
      try {
        networkManager.run();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }).start();

    final int ticksPerSecond = 20;
    final int millisecondsPerSecond = 1000;
    final int sleepMilliseconds = millisecondsPerSecond / ticksPerSecond;

    while (true) {
      while (hasEvent()) {
        handleEvent();
      }
      Thread.sleep(sleepMilliseconds);
    }
  }

  public void dispatchEvent(Event event) {
    eventQueue.dispatchEvent(event);
  }

  public void handleEvent() {
    eventQueue.handleEvent();
  }

  public boolean hasEvent() {
    return !eventQueue.isEmpty();
  }

  public void connectLocalClient(LocalConnectionBridge localConnectionBridge) {
    networkManager.startLocalConnection(localConnectionBridge);
  }
}
