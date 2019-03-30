package com.shepherdjerred.capstone.server;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.shepherdjerred.capstone.common.chat.ChatHistory;
import com.shepherdjerred.capstone.common.chat.ChatMessage;
import com.shepherdjerred.capstone.common.player.Player;
import com.shepherdjerred.capstone.events.Event;
import com.shepherdjerred.capstone.events.EventBus;
import com.shepherdjerred.capstone.events.handlers.EventLoggerHandler;
import com.shepherdjerred.capstone.server.events.events.PlayerChatEvent;
import com.shepherdjerred.capstone.server.events.events.network.ClientConnectedEvent;
import com.shepherdjerred.capstone.server.events.events.network.PacketReceivedEvent;
import com.shepherdjerred.capstone.server.events.handlers.ClientConnectedEventHandler;
import com.shepherdjerred.capstone.server.events.handlers.PacketReceivedEventHandler;
import com.shepherdjerred.capstone.server.events.handlers.PlayerChatEventHandler;
import com.shepherdjerred.capstone.server.network.ClientId;
import com.shepherdjerred.capstone.server.network.Connector;
import com.shepherdjerred.capstone.server.network.ConnectorHub;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;

@Log4j2
@ToString
public class GameServer {

  @Getter
  private ChatHistory chatHistory;
  private final Map<ClientId, Player> clientIdPlayerMap;
  private final ConnectorHub connectorHub;
  private final EventBus<Event> eventQueue;
  private final BiMap<ClientId, Player> handlePlayerMap;

  public GameServer() {
    this.chatHistory = new ChatHistory();
    this.eventQueue = new EventBus<>();
    clientIdPlayerMap = new HashMap<>();
    this.connectorHub = new ConnectorHub(eventQueue);
    handlePlayerMap = HashBiMap.create();
    registerNetworkEventHandlers();
    registerEventHandlers();
  }

  public void addChatMessage(ChatMessage message) {
    chatHistory = chatHistory.addMessage(message);
  }

  public void addPlayer(ClientId clientId, Player player) {
    handlePlayerMap.put(clientId, player);
  }

  private void registerNetworkEventHandlers() {
    eventQueue.registerHandler(new EventLoggerHandler<>());
    eventQueue.registerHandler(ClientConnectedEvent.class,
        new ClientConnectedEventHandler(connectorHub));
  }

  private void registerEventHandlers() {
    eventQueue.registerHandler(PacketReceivedEvent.class,
        new PacketReceivedEventHandler(this));
    eventQueue.registerHandler(PlayerChatEvent.class,
        new PlayerChatEventHandler(this, connectorHub));
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
    connectorHub.handleLatestEvents();
  }

  public void registerConnector(Connector connector) {
    connectorHub.registerConnector(connector);
  }

  public void dispatch(Event event) {
    eventQueue.dispatch(event);
  }

  public Player getPlayerByClientId(ClientId clientId) {
    return handlePlayerMap.get(clientId);
  }
}
