package com.shepherdjerred.capstone.server.network.netty;

import com.shepherdjerred.capstone.network.packet.packets.ServerBroadcastPacket;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

public class NettyServerBroadcast {

  public void broadcast(NettyServerSettings serverSettings) {
    EventLoopGroup group = new NioEventLoopGroup();

    try {
      Bootstrap b = new Bootstrap();
      b.group(group)
          .channel(NioDatagramChannel.class)
          .option(ChannelOption.SO_BROADCAST, true)
          .handler(new NettyServerBroadcastHandler());

      Channel ch = b.bind(serverSettings.getPort()).sync().channel();


      // Broadcast the request to port 8080.
      //TODO:ask about lobby settings
      ch.writeAndFlush(new ServerBroadcastPacket(serverSettings.getHostname(), serverSettings.getLobbySettings()));

      // Will close the DatagramChannel when a
      // response is received.  If the channel is not closed within 5 seconds,
      // print an error message and quit.
      if (!ch.closeFuture().await(5000)) {
        System.err.println("Quoridor request timed out.");
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      group.shutdownGracefully();
    }
  }
}
