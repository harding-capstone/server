package com.shepherdjerred.capstone.server.network.netty;

import com.shepherdjerred.capstone.network.netty.PacketCodec;
import com.shepherdjerred.capstone.network.netty.handlers.ExceptionLoggerHandler;
import com.shepherdjerred.capstone.network.packet.serialization.PacketJsonSerializer;
import com.shepherdjerred.capstone.server.network.ClientHandle;
import com.shepherdjerred.capstone.server.network.events.NetworkEvent;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PacketChannelInitializer extends ChannelInitializer<SocketChannel> {

  private final ConcurrentLinkedQueue<NetworkEvent> eventQueue;

  @Override
  protected void initChannel(SocketChannel socketChannel) {
    var clientHandle = new ClientHandle(UUID.randomUUID());
    var pipeline = socketChannel.pipeline();
    var serializer = new PacketJsonSerializer();

    pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4));
    pipeline.addLast(new PacketCodec(serializer));
    pipeline.addLast(new ChannelHandler(clientHandle, eventQueue));
    pipeline.addLast(new ExceptionLoggerHandler());
  }
}