package com.shepherdjerred.capstone.server.network.manager;

import com.shepherdjerred.capstone.common.lobby.Lobby;
import com.shepherdjerred.capstone.events.Event;
import com.shepherdjerred.capstone.events.EventBus;
import com.shepherdjerred.capstone.server.network.broadcast.ServerBroadcast;
import com.shepherdjerred.capstone.server.network.broadcast.netty.NettyBroadcast;
import com.shepherdjerred.capstone.server.network.event.events.PacketReceivedEvent;
import com.shepherdjerred.capstone.server.network.event.handlers.PacketReceivedEventHandler;
import com.shepherdjerred.capstone.server.network.manager.events.StartBroadcastEvent;
import com.shepherdjerred.capstone.server.network.manager.events.StopBroadcastEvent;
import com.shepherdjerred.capstone.server.network.server.netty.NettyServerBootstrap;
import java.net.SocketAddress;

public class NetworkManager {

  private final SocketAddress broadcastAddress;
  private final SocketAddress gameAddress;
  private final EventBus<Event> eventBus;
  private NettyServerBootstrap networkBootstrap;
  private ServerBroadcast serverBroadcast;
  private Thread networkThread;
  private Thread broadcastThread;

  public NetworkManager(SocketAddress gameAddress,
      SocketAddress broadcastAddress,
      EventBus<Event> eventBus) {
    this.broadcastAddress = broadcastAddress;
    this.gameAddress = gameAddress;
    this.eventBus = eventBus;

  }

  private void createEventHandlers() {
    eventBus.registerHandler(PacketReceivedEvent.class, new PacketReceivedEventHandler(eventBus));

    eventBus.registerHandler(StartBroadcastEvent.class, (event) -> {

    });

    eventBus.registerHandler(StopBroadcastEvent.class, (event) -> {

    });
  }

  private void startBroadcast(Lobby lobby) {
    serverBroadcast = new NettyBroadcast(broadcastAddress, eventBus, lobby);
    broadcastThread = new Thread(serverBroadcast, "BROADCAST");
  }

  private void stopBroadcast() {
    serverBroadcast.stop();
  }

  private void startNetwork() {
    networkBootstrap = new NettyServerBootstrap(gameAddress);
    networkThread = new Thread(networkBootstrap, "NETWORK");
  }

  private void stopNetwork() {
    networkBootstrap.shutdown();
  }

  public void update() {
    var event = networkBootstrap.getNextEvent();
    event.ifPresent(eventBus::dispatch);
  }
}
