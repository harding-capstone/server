package com.shepherdjerred.capstone.server.server;

import com.shepherdjerred.capstone.common.chat.ChatHistory;
import com.shepherdjerred.capstone.common.lobby.Lobby;
import com.shepherdjerred.capstone.common.player.Player;
import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.player.PlayerId;
import com.shepherdjerred.capstone.server.events.Event;
import com.shepherdjerred.capstone.server.events.client.ClientAcceptedEvent;
import com.shepherdjerred.capstone.server.events.client.ClientConnectedEvent;
import com.shepherdjerred.capstone.server.events.handler.EventLoggerHandler;
import com.shepherdjerred.capstone.server.events.handler.ThreadSafeEventQueue;
import com.shepherdjerred.capstone.server.events.network.ReceivedEventEvent;
import com.shepherdjerred.capstone.server.events.player.PlayerInfoEvent;
import com.shepherdjerred.capstone.server.events.player.PlayerJoinEvent;
import com.shepherdjerred.capstone.server.network.ClientManager;
import com.shepherdjerred.capstone.server.server.handlers.ClientAcceptedEventHandler;
import com.shepherdjerred.capstone.server.server.handlers.ReceivedEventEventHandler;
import com.shepherdjerred.capstone.server.network.local.LocalBridge;
import com.shepherdjerred.capstone.server.server.handlers.ClientConnectedEventHandler;
import com.shepherdjerred.capstone.server.server.handlers.PlayerInfoEventHandler;
import com.shepherdjerred.capstone.server.server.handlers.PlayerJoinEventHandler;
import lombok.ToString;

@ToString
public class Server {

  private final ClientManager clientManager;
  private final ServerSettings serverSettings;
  private final ThreadSafeEventQueue eventQueue;
  private ChatHistory chatHistory;
  private Match match;
  private Lobby lobby;

  public Server(ServerSettings serverSettings,
      ChatHistory chatHistory,
      Match match,
      Lobby lobby) {
    this.serverSettings = serverSettings;
    this.chatHistory = chatHistory;
    this.match = match;
    this.lobby = lobby;
    this.eventQueue = new ThreadSafeEventQueue();
    this.clientManager = new ClientManager(eventQueue);
    registerHandlers();
  }

  private void registerHandlers() {
    eventQueue.registerHandler(Event.class, new EventLoggerHandler());
    eventQueue.registerHandler(ClientConnectedEvent.class, new ClientConnectedEventHandler(this));
    eventQueue.registerHandler(ClientAcceptedEvent.class,
        new ClientAcceptedEventHandler(clientManager));
    eventQueue.registerHandler(ReceivedEventEvent.class, new ReceivedEventEventHandler(eventQueue));
    eventQueue.registerHandler(PlayerInfoEvent.class, new PlayerInfoEventHandler(this));
    eventQueue.registerHandler(PlayerJoinEvent.class, new PlayerJoinEventHandler(this));
  }

  public void addPlayer(Player player) {
    // TODO get next open player ID somehow instead of hardcoding
    lobby.addPlayer(PlayerId.ONE, player);
  }

  public void loop() {
    clientManager.pullLatestEvents();
    while (hasEvent()) {
      handleEvent();
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

  public void connectLocalClient(LocalBridge localBridge) {
    clientManager.startLocalConnection(localBridge);
  }
}
