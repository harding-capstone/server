package com.shepherdjerred.capstone.server.client;

import com.shepherdjerred.capstone.network.netty.PacketCodec;
import com.shepherdjerred.capstone.network.netty.handlers.ExceptionLoggerHandler;
import com.shepherdjerred.capstone.network.packet.serialization.PacketJsonSerializer;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ClientInitializer extends ChannelInitializer<SocketChannel> {

  @Override
  protected void initChannel(SocketChannel socketChannel) {
    var pipeline = socketChannel.pipeline();
    var serializer = new PacketJsonSerializer();

    pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4));
    pipeline.addLast(new PacketCodec(serializer));
    pipeline.addLast(new ClientHandler());
    pipeline.addLast(new ExceptionLoggerHandler());
  }

}
