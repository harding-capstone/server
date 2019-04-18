package com.shepherdjerred.capstone.server.network.netty;

import com.shepherdjerred.capstone.server.event.events.network.NetworkEvent;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import java.net.SocketAddress;
import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedQueue;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class NettyServerBootstrap implements Runnable {

  private final ConcurrentLinkedQueue<NetworkEvent> eventQueue;
  private final SocketAddress address;

  public NettyServerBootstrap(SocketAddress address) {
    this.address = address;
    this.eventQueue = new ConcurrentLinkedQueue<>();
  }

  @Override
  public void run() {
    EventLoopGroup group = new NioEventLoopGroup();

    try {
      ServerBootstrap serverBootstrap = new ServerBootstrap()
          .group(group)
          .channel(NioServerSocketChannel.class)
          .childHandler(new ServerChannelInitializer(eventQueue))
          .localAddress(address)
          .option(ChannelOption.SO_REUSEADDR, true);

      ChannelFuture channel = serverBootstrap.bind().sync();
      channel.channel().closeFuture().sync();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        group.shutdownGracefully().sync();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  public Optional<NetworkEvent> getNextEvent() {
    if (eventQueue.size() > 0) {
      return Optional.of(eventQueue.poll());
    } else {
      return Optional.empty();
    }
  }
}
