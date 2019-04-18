package com.shepherdjerred.capstone.server.network.broadcast.netty;

import static com.shepherdjerred.capstone.common.Constants.DISCOVERY_PORT;

import com.shepherdjerred.capstone.common.lobby.LobbySettings;
import com.shepherdjerred.capstone.events.Event;
import com.shepherdjerred.capstone.events.EventBus;
import com.shepherdjerred.capstone.server.network.broadcast.ServerBroadcast;
import java.net.InetSocketAddress;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@AllArgsConstructor
public class NettyBroadcast implements ServerBroadcast {

  private final EventBus<Event> eventBus;
  private final LobbySettings lobbySettings;

  @Override
  public void broadcast() {
    new NettyBroadcastBootstrap(new InetSocketAddress(DISCOVERY_PORT), lobbySettings, eventBus).run();
  }

  @Override
  public void run() {
    log.info("Broadcasting...");
    broadcast();
  }
}
