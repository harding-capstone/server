package com.shepherdjerred.capstone.server.network.broadcast.netty;

import com.shepherdjerred.capstone.common.lobby.LobbySettings;
import com.shepherdjerred.capstone.events.Event;
import com.shepherdjerred.capstone.events.EventBus;
import com.shepherdjerred.capstone.events.handlers.EventHandlerFrame;
import com.shepherdjerred.capstone.network.packet.packets.ServerBroadcastPacket;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import java.net.SocketAddress;
import java.util.concurrent.TimeUnit;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class NettyBroadcastBootstrap implements Runnable {

  private final SocketAddress address;
  private final EventBus<Event> eventBus;
  private EventLoopGroup eventLoopGroup;
  private LobbySettings lobbySettings;
  private final EventHandlerFrame<Event> eventHandlerFrame;

  public NettyBroadcastBootstrap(SocketAddress address,
      LobbySettings lobbySettings,
      EventBus<Event> eventBus) {
    this.address = address;
    this.eventBus = eventBus;
    this.lobbySettings = lobbySettings;
    this.eventHandlerFrame = new EventHandlerFrame<>();
    registerEventHandlers();
  }

  private void registerEventHandlers() {
    // TODO handle lobby changes
    eventBus.removeHandlerFrame(eventHandlerFrame);
  }

  @Override
  public void run() {
    eventLoopGroup = new NioEventLoopGroup();

    try {
      var bootstrap = new Bootstrap();
      bootstrap.group(eventLoopGroup)
          .channel(NioDatagramChannel.class)
          .handler(new NettyBroadcastInitializer())
          .option(ChannelOption.SO_BROADCAST, true)
          .option(ChannelOption.SO_REUSEADDR, true);

      var channel = bootstrap.bind(address).sync().channel();

      eventLoopGroup.scheduleAtFixedRate(() -> {
            log.info("Broadcasting");
            channel.writeAndFlush(new ServerBroadcastPacket(lobbySettings));
          },
          0,
          2,
          TimeUnit.SECONDS);

      channel.closeFuture().sync();

    } catch (InterruptedException e) {
      log.error(e);
    } finally {
      cleanup();
    }
  }

  public void cleanup() {
    eventLoopGroup.shutdownGracefully().syncUninterruptibly();
  }
}
