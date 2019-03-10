package com.shepherdjerred.capstone.server.server.clients.netty;

import com.shepherdjerred.capstone.server.server.clients.ClientHandle;
import com.shepherdjerred.capstone.server.server.clients.event.events.ConnectorEvent;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import java.util.Queue;
import java.util.UUID;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {

  private final Queue<ConnectorEvent> eventQueue;

  @Override
  protected void initChannel(SocketChannel socketChannel) {
    var clientHandle = new ClientHandle(UUID.randomUUID());
    var pipeline = socketChannel.pipeline();
    pipeline.addLast(new ServerHandler(clientHandle, eventQueue));
  }
}
