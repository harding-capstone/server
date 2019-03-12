package com.shepherdjerred.capstone.server.network.netty;

import com.shepherdjerred.capstone.server.network.ClientConnector;
import com.shepherdjerred.capstone.server.network.events.NetworkEvent;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import java.net.InetSocketAddress;
import java.util.concurrent.ConcurrentLinkedQueue;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class NettyTcpConnector implements ClientConnector {

  private final ConcurrentLinkedQueue<NetworkEvent> eventQueue;
  private final NettySettings nettySettings;

  public NettyTcpConnector(NettySettings nettySettings) {
    this.eventQueue = new ConcurrentLinkedQueue<>();
    this.nettySettings = nettySettings;
  }

  @Override
  public void acceptConnections() {
    new Thread(() -> {
      EventLoopGroup group = new NioEventLoopGroup();

      try {
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        serverBootstrap.group(group);
        serverBootstrap.channel(NioServerSocketChannel.class);
        serverBootstrap.localAddress(new InetSocketAddress(nettySettings.getHostname(),
            nettySettings.getPort()));
        serverBootstrap.childHandler(new PacketChannelInitializer(eventQueue));

        ChannelFuture channelFuture = serverBootstrap.bind().sync();
        channelFuture.channel().closeFuture().sync();
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        try {
          group.shutdownGracefully().sync();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }).start();
  }

  @Override
  public NetworkEvent getNextEvent() {
    return eventQueue.remove();
  }

  @Override
  public boolean hasEvent() {
    return !eventQueue.isEmpty();
  }
}
