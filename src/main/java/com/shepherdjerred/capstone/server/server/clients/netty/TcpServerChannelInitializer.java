package com.shepherdjerred.capstone.server.server.clients.netty;

import com.shepherdjerred.capstone.server.packets.ByteToPacketDecoder;
import com.shepherdjerred.capstone.server.packets.PacketToByteEncoder;
import com.shepherdjerred.capstone.server.packets.serialization.PacketJsonSerializer;
import com.shepherdjerred.capstone.server.server.clients.ClientHandle;
import com.shepherdjerred.capstone.server.server.clients.events.ConnectorEvent;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TcpServerChannelInitializer extends ChannelInitializer<SocketChannel> {

  private final ConcurrentLinkedQueue<ConnectorEvent> eventQueue;

  @Override
  protected void initChannel(SocketChannel socketChannel) {
    var clientHandle = new ClientHandle(UUID.randomUUID());
    var pipeline = socketChannel.pipeline();
    var serializer = new PacketJsonSerializer();
    pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4));
    pipeline.addLast(new ByteToPacketDecoder(serializer));
    pipeline.addLast(new PacketToByteEncoder(serializer));
    pipeline.addLast(new TcpServerHandler(clientHandle, eventQueue));
  }
}
