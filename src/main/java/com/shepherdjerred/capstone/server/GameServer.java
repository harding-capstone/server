package com.shepherdjerred.capstone.server;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.shepherdjerred.capstone.common.chat.ChatHistory;
import com.shepherdjerred.capstone.common.chat.ChatMessage;
import com.shepherdjerred.capstone.common.lobby.Lobby;
import com.shepherdjerred.capstone.common.lobby.LobbySettings;
import com.shepherdjerred.capstone.common.player.Element;
import com.shepherdjerred.capstone.common.player.Player;
import com.shepherdjerred.capstone.events.Event;
import com.shepherdjerred.capstone.events.EventBus;
import com.shepherdjerred.capstone.events.handlers.EventLoggerHandler;
import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.turn.Turn;
import com.shepherdjerred.capstone.server.events.events.EditLobbyEvent;
import com.shepherdjerred.capstone.server.events.events.HostLeaveEvent;
import com.shepherdjerred.capstone.server.events.events.PlayerChatEvent;
import com.shepherdjerred.capstone.server.events.events.PlayerEvictEvent;
import com.shepherdjerred.capstone.server.events.events.PlayerJoinEvent;
import com.shepherdjerred.capstone.server.events.events.PlayerLeaveEvent;
import com.shepherdjerred.capstone.server.events.events.StartGameEvent;
import com.shepherdjerred.capstone.server.events.events.TurnEvent;
import com.shepherdjerred.capstone.server.events.events.network.ClientConnectedEvent;
import com.shepherdjerred.capstone.server.events.events.network.ClientDisconnectedEvent;
import com.shepherdjerred.capstone.server.events.events.network.PacketReceivedEvent;
import com.shepherdjerred.capstone.server.events.exception.LobbyFullException;
import com.shepherdjerred.capstone.server.events.handlers.ClientConnectedEventHandler;
import com.shepherdjerred.capstone.server.events.handlers.ClientDisconnectedEventHandler;
import com.shepherdjerred.capstone.server.events.handlers.EditLobbyEventHandler;
import com.shepherdjerred.capstone.server.events.handlers.HostLeaveEventHandler;
import com.shepherdjerred.capstone.server.events.handlers.PacketReceivedEventHandler;
import com.shepherdjerred.capstone.server.events.handlers.PlayerChatEventHandler;
import com.shepherdjerred.capstone.server.events.handlers.PlayerEvictEventHandler;
import com.shepherdjerred.capstone.server.events.handlers.PlayerJoinEventHandler;
import com.shepherdjerred.capstone.server.events.handlers.PlayerLeaveEventHandler;
import com.shepherdjerred.capstone.server.events.handlers.StartGameEventHandler;
import com.shepherdjerred.capstone.server.events.handlers.TurnEventHandler;
import com.shepherdjerred.capstone.server.network.ClientId;
import com.shepherdjerred.capstone.server.network.Connector;
import com.shepherdjerred.capstone.server.network.ConnectorHub;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;

@Log4j2
@ToString
public class GameServer {

  @Getter
  private ChatHistory chatHistory;
  private final ConnectorHub connectorHub;
  private final EventBus<Event> eventQueue;
  private final BiMap<ClientId, Player> handlePlayerMap;
  @Getter
  private Lobby lobby;
  @Getter
  @Setter
  private Match match;

  public GameServer(LobbySettings lobbySettings) {
    this.chatHistory = new ChatHistory();
    this.eventQueue = new EventBus<>();
    this.connectorHub = new ConnectorHub(eventQueue);
    handlePlayerMap = HashBiMap.create();
    lobby = Lobby.from(lobbySettings);
    registerNetworkEventHandlers();
    registerEventHandlers();
  }

  public void MakeTurn(Turn turn) {
    match.doTurn(turn);
  }

  public void updateLobby(LobbySettings lobbySettings) {
   lobby = lobby.UpdateLobbySettings(lobbySettings);
  }

  public void addChatMessage(ChatMessage message) {
    chatHistory = chatHistory.addMessage(message);
  }

  public void addPlayer(ClientId clientId, Player player) throws LobbyFullException {
    if (!lobby.isFull()) {
      handlePlayerMap.put(clientId, player);
      lobby.addPlayer(player);
    } else {
      throw new LobbyFullException();
    }

  }

  public Element getNextElement() {
    return lobby.getNextElement();
  }

  public void removePlayer(ClientId clientId) {
    removePlayer(clientId, handlePlayerMap.get(clientId));
  }

  public void removePlayer(ClientId clientId, Player player) {
      handlePlayerMap.remove(clientId, player);
      lobby.removePlayer(player);
  }

  public void removePlayer(Player player) {
    removePlayer(handlePlayerMap.inverse().get(player), player);
  }


  private void registerNetworkEventHandlers() {
    eventQueue.registerHandler(new EventLoggerHandler<>());
    eventQueue.registerHandler(ClientConnectedEvent.class,
        new ClientConnectedEventHandler(connectorHub));
    eventQueue.registerHandler(ClientDisconnectedEvent.class,
        new ClientDisconnectedEventHandler(this, connectorHub));
    eventQueue.registerHandler(PacketReceivedEvent.class,
        new PacketReceivedEventHandler(this));
  }

  private void registerEventHandlers() {
    eventQueue.registerHandler(PacketReceivedEvent.class,
        new PacketReceivedEventHandler(this));
    eventQueue.registerHandler(PlayerChatEvent.class,
        new PlayerChatEventHandler(this, connectorHub));
    eventQueue.registerHandler(ClientDisconnectedEvent.class,
        new ClientDisconnectedEventHandler(this, connectorHub));
    eventQueue.registerHandler(EditLobbyEvent.class,
        new EditLobbyEventHandler(this, connectorHub));
    eventQueue.registerHandler(HostLeaveEvent.class,
        new HostLeaveEventHandler(this, connectorHub));
    eventQueue.registerHandler(PlayerEvictEvent.class,
        new PlayerEvictEventHandler(this, connectorHub));
    eventQueue.registerHandler(PlayerJoinEvent.class,
        new PlayerJoinEventHandler(this, connectorHub));
    eventQueue.registerHandler(PlayerLeaveEvent.class,
        new PlayerLeaveEventHandler(this, connectorHub));
    eventQueue.registerHandler(StartGameEvent.class,
        new StartGameEventHandler(this, connectorHub));
    eventQueue.registerHandler(TurnEvent.class,
        new TurnEventHandler(this, connectorHub));
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
