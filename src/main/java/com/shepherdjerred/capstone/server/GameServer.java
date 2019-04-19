package com.shepherdjerred.capstone.server;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.shepherdjerred.capstone.common.Constants;
import com.shepherdjerred.capstone.common.GameState;
import com.shepherdjerred.capstone.common.chat.ChatHistory;
import com.shepherdjerred.capstone.common.lobby.Lobby;
import com.shepherdjerred.capstone.common.player.Player;
import com.shepherdjerred.capstone.events.Event;
import com.shepherdjerred.capstone.events.EventBus;
import com.shepherdjerred.capstone.events.handlers.EventLoggerHandler;
import com.shepherdjerred.capstone.network.packet.packets.Packet;
import com.shepherdjerred.capstone.server.event.events.network.PacketReceivedEvent;
import com.shepherdjerred.capstone.server.event.handlers.PacketReceivedEventHandler;
import com.shepherdjerred.capstone.server.game.LobbyServerState;
import com.shepherdjerred.capstone.server.game.ServerState;
import com.shepherdjerred.capstone.server.network.Connection;
import com.shepherdjerred.capstone.server.network.broadcast.ServerBroadcast;
import com.shepherdjerred.capstone.server.network.broadcast.netty.NettyBroadcast;
import com.shepherdjerred.capstone.server.network.netty.NettyServerBootstrap;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;

@Log4j2
@ToString
public class GameServer implements Runnable {

  private boolean shouldContinue = true;
  private final BiMap<Player, Connection> playerMap;
  private final EventBus<Event> eventBus;
  private final ServerBroadcast serverBroadcast;
  private final NettyServerBootstrap bootstrap;
  private final Thread networkThread;
  private final Thread discoveryThread;
  private ServerState serverState;
  private GameState gameState;

  public static void main(String[] args) {
    var server = new GameServer(new InetSocketAddress(Constants.GAME_PORT),
        new InetSocketAddress(Constants.DISCOVERY_PORT));
    server.run();
  }

  public GameServer(SocketAddress serverAddress, SocketAddress broadcastAddress) {
    playerMap = HashBiMap.create();
    eventBus = new EventBus<>();
    serverState = new LobbyServerState(this, eventBus);
    gameState = new GameState(Lobby.fromDefaultLobbySettings(), null, new ChatHistory());
    serverBroadcast = new NettyBroadcast(broadcastAddress,
        eventBus,
        gameState.getLobby().getLobbySettings());
    bootstrap = new NettyServerBootstrap(serverAddress);
    networkThread = new Thread(bootstrap, "SERVER_NETWORK_THREAD");
    discoveryThread = new Thread(serverBroadcast, "DISCOVERY_THREAD");
    eventBus.registerHandler(new EventLoggerHandler<>());
    eventBus.registerHandler(PacketReceivedEvent.class, new PacketReceivedEventHandler(eventBus));
  }

  public void setPlayerConnection(Player player, Connection connection) {
    playerMap.put(player, connection);
  }

  public Connection getConnection(Player player) {
    return playerMap.get(player);
  }

  public void send(Packet packet) {
    playerMap.values().forEach(connection -> connection.sendPacket(packet));
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
