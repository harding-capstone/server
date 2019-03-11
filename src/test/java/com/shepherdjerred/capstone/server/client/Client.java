package com.shepherdjerred.capstone.server.client;

import com.shepherdjerred.capstone.server.packets.ByteToPacketDecoder;
import com.shepherdjerred.capstone.server.packets.PacketToByteEncoder;
import com.shepherdjerred.capstone.server.packets.serialization.PacketJsonSerializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import java.net.InetSocketAddress;

public class Client {
  public void start() throws InterruptedException {
    EventLoopGroup group = new NioEventLoopGroup();
    try{
      Bootstrap clientBootstrap = new Bootstrap();

      clientBootstrap.group(group);
      clientBootstrap.channel(NioSocketChannel.class);
      clientBootstrap.remoteAddress(new InetSocketAddress("localhost", 9999));
      clientBootstrap.handler(new ChannelInitializer<SocketChannel>() {
        protected void initChannel(SocketChannel socketChannel) {
          var pipeline = socketChannel.pipeline();
          var serializer = new PacketJsonSerializer();
          pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4));
          pipeline.addLast(new ByteToPacketDecoder(serializer));
          pipeline.addLast(new PacketToByteEncoder(serializer));
          pipeline.addLast(new ClientHandler());
        }
      });
      ChannelFuture channelFuture = clientBootstrap.connect().sync();
      channelFuture.channel().closeFuture().sync();
    } finally {
      group.shutdownGracefully().sync();
    }
  }
}
