package com.shepherdjerred.capstone.server;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.shepherdjerred.capstone.common.GameState;
import com.shepherdjerred.capstone.common.chat.ChatHistory;
import com.shepherdjerred.capstone.common.lobby.Lobby;
import com.shepherdjerred.capstone.common.player.Player;
import com.shepherdjerred.capstone.events.Event;
import com.shepherdjerred.capstone.events.EventBus;
import com.shepherdjerred.capstone.server.game.LobbyServerState;
import com.shepherdjerred.capstone.server.game.ServerState;
import com.shepherdjerred.capstone.server.network.ClientId;
import com.shepherdjerred.capstone.server.network.broadcast.ServerBroadcast;
import com.shepherdjerred.capstone.server.network.broadcast.netty.NettyBroadcast;
import com.shepherdjerred.capstone.server.network.netty.NettyServerBootstrap;
import java.net.SocketAddress;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;

@Log4j2
@ToString
public class GameServer implements Runnable {

  private boolean shouldContinue = true;
  private final EventBus<Event> eventBus;
  private final BiMap<ClientId, Player> handlePlayerMap;
  private final ServerBroadcast serverBroadcast;
  private final NettyServerBootstrap bootstrap;
  private final Thread networkThread;
  private final Thread discoveryThread;
  private ServerState serverState;
  private GameState gameState;

  public GameServer(SocketAddress serverAddress, SocketAddress broadcastAddress) {
    eventBus = new EventBus<>();
    serverState = new LobbyServerState(eventBus);
    gameState = new GameState(Lobby.fromDefaultLobbySettings(), null, new ChatHistory());
    handlePlayerMap = HashBiMap.create();
    serverBroadcast = new NettyBroadcast(broadcastAddress,
        eventBus,
        gameState.getLobby().getLobbySettings());
    bootstrap = new NettyServerBootstrap(serverAddress);
    networkThread = new Thread(bootstrap, "SERVER_NETWORK_THREAD");
    discoveryThread = new Thread(serverBroadcast, "DISCOVERY_THREAD");
  }

  @Override
  public void run() {
    final int ticksPerSecond = 30;
    final int millisecondsPerSecond = 1000;
    final int sleepMilliseconds = millisecondsPerSecond / ticksPerSecond;

    serverState.enable();
    networkThread.start();
    discoveryThread.start();

    while (shouldContinue) {
      var event = bootstrap.getNextEvent();
      event.ifPresent(eventBus::dispatch);

      try {
        Thread.sleep(sleepMilliseconds);
      } catch (InterruptedException e) {
        log.error(e);
      }
    }
  }
}
