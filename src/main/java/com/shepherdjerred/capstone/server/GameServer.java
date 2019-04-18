package com.shepherdjerred.capstone.server;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.shepherdjerred.capstone.common.GameState;
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
import com.shepherdjerred.capstone.server.event.events.EditLobbyEvent;
import com.shepherdjerred.capstone.server.event.events.HostLeaveEvent;
import com.shepherdjerred.capstone.server.event.events.PlayerChatEvent;
import com.shepherdjerred.capstone.server.event.events.PlayerEvictEvent;
import com.shepherdjerred.capstone.server.event.events.PlayerJoinEvent;
import com.shepherdjerred.capstone.server.event.events.PlayerLeaveEvent;
import com.shepherdjerred.capstone.server.event.events.StartGameEvent;
import com.shepherdjerred.capstone.server.event.events.TurnEvent;
import com.shepherdjerred.capstone.server.event.events.network.ClientConnectedEvent;
import com.shepherdjerred.capstone.server.event.events.network.ClientDisconnectedEvent;
import com.shepherdjerred.capstone.server.event.events.network.PacketReceivedEvent;
import com.shepherdjerred.capstone.server.event.exception.LobbyFullException;
import com.shepherdjerred.capstone.server.event.handlers.ClientConnectedEventHandler;
import com.shepherdjerred.capstone.server.event.handlers.ClientDisconnectedEventHandler;
import com.shepherdjerred.capstone.server.event.handlers.EditLobbyEventHandler;
import com.shepherdjerred.capstone.server.event.handlers.HostLeaveEventHandler;
import com.shepherdjerred.capstone.server.event.handlers.PacketReceivedEventHandler;
import com.shepherdjerred.capstone.server.event.handlers.PlayerChatEventHandler;
import com.shepherdjerred.capstone.server.event.handlers.PlayerEvictEventHandler;
import com.shepherdjerred.capstone.server.event.handlers.PlayerJoinEventHandler;
import com.shepherdjerred.capstone.server.event.handlers.PlayerLeaveEventHandler;
import com.shepherdjerred.capstone.server.event.handlers.StartGameEventHandler;
import com.shepherdjerred.capstone.server.event.handlers.TurnEventHandler;
import com.shepherdjerred.capstone.server.network.ClientId;
import com.shepherdjerred.capstone.server.network.Connector;
import com.shepherdjerred.capstone.server.network.ConnectorHub;
import com.shepherdjerred.capstone.server.network.broadcast.ServerBroadcast;
import com.shepherdjerred.capstone.server.network.broadcast.netty.NettyBroadcast;
import java.util.Optional;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;

@Log4j2
@ToString
public class GameServer implements Runnable {

  private final ConnectorHub connectorHub;
  private final EventBus<Event> eventBus;
  private final BiMap<ClientId, Player> handlePlayerMap;
  private GameState gameState;
  private ServerBroadcast serverBroadcast;

  public GameServer() {
    this.eventBus = new EventBus<>();
    this.connectorHub = new ConnectorHub(eventBus);
    this.gameState = new GameState(Lobby.fromDefaultLobbySettings(), null, new ChatHistory());
    handlePlayerMap = HashBiMap.create();
    serverBroadcast = new NettyBroadcast(eventBus, gameState.getLobby().getLobbySettings());
    registerNetworkEventHandlers();
    registerEventHandlers();
  }

  public void broadcast() {
    new Thread(serverBroadcast, "BROADCAST").start();
  }

  public void createMatch() {
    var boardSettings = gameState.getLobby().getLobbySettings().getBoardSettings();
    var matchSettings = gameState.getLobby().getLobbySettings().getMatchSettings();
    gameState.setMatch(Match.from(matchSettings, boardSettings));
  }

  public Optional<Element> getFreeElement() {
    return gameState.getLobby().getFreeElement();
  }

  public boolean hasMatchStarted() {
    return gameState.getMatch() != null;
  }

  public void doTurn(Turn turn) {
    gameState = gameState.setMatch(gameState.getMatch().doTurn(turn));
  }

  public void updateLobbySettings(LobbySettings lobbySettings) {
    gameState = gameState.setLobby(gameState.getLobby().setLobbySettings(lobbySettings));
  }

  public void addMessage(ChatMessage message) {
    gameState = gameState.setChatHistory(gameState.getChatHistory().addMessage(message));
  }

  public void addPlayer(ClientId clientId, Player player) throws LobbyFullException {
    if (gameState.getLobby().hasFreeSlot()) {
      handlePlayerMap.put(clientId, player);
      gameState = gameState.setLobby(gameState.getLobby().addPlayer(player));
    } else {
      throw new LobbyFullException();
    }
  }

  public void removePlayer(ClientId clientId) {
    removePlayer(clientId, handlePlayerMap.get(clientId));
  }

  public void removePlayer(ClientId clientId, Player player) {
    handlePlayerMap.remove(clientId, player);
    gameState = gameState.setLobby(gameState.getLobby().removePlayer(player));
  }

  public void removePlayer(Player player) {
    removePlayer(handlePlayerMap.inverse().get(player), player);
  }

  private void registerNetworkEventHandlers() {
    eventBus.registerHandler(new EventLoggerHandler<>());
    eventBus.registerHandler(ClientConnectedEvent.class,
        new ClientConnectedEventHandler(connectorHub));
    eventBus.registerHandler(ClientDisconnectedEvent.class,
        new ClientDisconnectedEventHandler(this, connectorHub));
    eventBus.registerHandler(PacketReceivedEvent.class,
        new PacketReceivedEventHandler(this));
  }

  private void registerEventHandlers() {
    eventBus.registerHandler(PacketReceivedEvent.class,
        new PacketReceivedEventHandler(this));
    eventBus.registerHandler(PlayerChatEvent.class,
        new PlayerChatEventHandler(this, connectorHub));
    eventBus.registerHandler(ClientDisconnectedEvent.class,
        new ClientDisconnectedEventHandler(this, connectorHub));
    eventBus.registerHandler(EditLobbyEvent.class,
        new EditLobbyEventHandler(this, connectorHub));
    eventBus.registerHandler(HostLeaveEvent.class,
        new HostLeaveEventHandler(this, connectorHub));
    eventBus.registerHandler(PlayerEvictEvent.class,
        new PlayerEvictEventHandler(this, connectorHub));
    eventBus.registerHandler(PlayerJoinEvent.class,
        new PlayerJoinEventHandler(this, connectorHub));
    eventBus.registerHandler(PlayerLeaveEvent.class,
        new PlayerLeaveEventHandler(this, connectorHub));
    eventBus.registerHandler(StartGameEvent.class,
        new StartGameEventHandler(connectorHub));
    eventBus.registerHandler(TurnEvent.class,
        new TurnEventHandler(this, connectorHub));
  }

  @Override
  public void run() {
    final int ticksPerSecond = 10;
    final int millisecondsPerSecond = 1000;
    final int sleepMilliseconds = millisecondsPerSecond / ticksPerSecond;

    while (true) {
      connectorHub.handleLatestEvents();
      try {
        Thread.sleep(sleepMilliseconds);
      } catch (InterruptedException e) {
        log.error(e);
      }
    }
  }

  public void registerConnector(Connector connector) {
    connectorHub.registerConnector(connector);
  }

  public void dispatch(Event event) {
    eventBus.dispatch(event);
  }

  public Player getPlayerByClientId(ClientId clientId) {
    return handlePlayerMap.get(clientId);
  }
}
