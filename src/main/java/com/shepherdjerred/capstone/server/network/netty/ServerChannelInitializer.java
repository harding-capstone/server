package com.shepherdjerred.capstone.server.network.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {

  @Override
  protected void initChannel(SocketChannel socketChannel) throws Exception {
    var pipeline = socketChannel.pipeline();
    pipeline.addLast(new ServerHandler());
  }
}
