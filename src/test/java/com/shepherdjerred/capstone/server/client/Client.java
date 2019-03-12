package com.shepherdjerred.capstone.server.client;

import com.shepherdjerred.capstone.server.network.netty.PacketChannelInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import java.net.InetSocketAddress;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Client {
  public void start() throws InterruptedException {
    EventLoopGroup group = new NioEventLoopGroup();
    try{
      Bootstrap clientBootstrap = new Bootstrap();

      clientBootstrap.group(group);
      clientBootstrap.channel(NioSocketChannel.class);
      clientBootstrap.remoteAddress(new InetSocketAddress("localhost", 9999));
      clientBootstrap.handler(new PacketChannelInitializer(new ConcurrentLinkedQueue<>()));
      ChannelFuture channelFuture = clientBootstrap.connect().sync();
      channelFuture.channel().closeFuture().sync();
    } finally {
      group.shutdownGracefully().sync();
    }
  }
}
