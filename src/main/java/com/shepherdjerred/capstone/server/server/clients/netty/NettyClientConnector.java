package com.shepherdjerred.capstone.server.server.clients.netty;

import com.shepherdjerred.capstone.server.server.clients.ClientConnector;
import com.shepherdjerred.capstone.server.server.clients.event.events.ConnectorEvent;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import java.net.InetSocketAddress;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class NettyClientConnector implements ClientConnector {

  private final Queue<ConnectorEvent> eventQueue;
  private final NettySettings nettySettings;

  public NettyClientConnector(NettySettings nettySettings) {
    this.eventQueue = new ConcurrentLinkedQueue<>();
    this.nettySettings = nettySettings;
  }

  @Override
  public void acceptConnections() throws InterruptedException {
    log.info("Listening");

    EventLoopGroup group = new NioEventLoopGroup();

    try {
      ServerBootstrap serverBootstrap = new ServerBootstrap();

      serverBootstrap.group(group);
      serverBootstrap.channel(NioServerSocketChannel.class);
      serverBootstrap.localAddress(new InetSocketAddress(nettySettings.getHostname(),
          nettySettings.getPort()));
      serverBootstrap.childHandler(new ServerChannelInitializer(eventQueue));

      ChannelFuture channelFuture = serverBootstrap.bind().sync();
      channelFuture.channel().closeFuture().sync();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      group.shutdownGracefully().sync();
    }
  }

  @Override
  public ConnectorEvent getLatestEvent() {
    return eventQueue.remove();
  }

  @Override
  public boolean hasEvent() {
    return !eventQueue.isEmpty();
  }
}
