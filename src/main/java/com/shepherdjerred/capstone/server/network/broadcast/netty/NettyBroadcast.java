package com.shepherdjerred.capstone.server.network.broadcast.netty;

import com.shepherdjerred.capstone.common.lobby.LobbySettings;
import com.shepherdjerred.capstone.events.Event;
import com.shepherdjerred.capstone.events.EventBus;
import com.shepherdjerred.capstone.server.network.broadcast.ServerBroadcast;
import java.net.SocketAddress;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@AllArgsConstructor
public class NettyBroadcast implements ServerBroadcast {

  private final SocketAddress address;
  private final EventBus<Event> eventBus;
  private final LobbySettings lobbySettings;

  @Override
  public void run() {
    new NettyBroadcastBootstrap(address, lobbySettings, eventBus).run();
  }
}
