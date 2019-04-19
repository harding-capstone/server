package com.shepherdjerred.capstone.server.network.netty;

import com.shepherdjerred.capstone.network.packet.packets.Packet;
import com.shepherdjerred.capstone.server.event.events.network.ClientConnectedEvent;
import com.shepherdjerred.capstone.server.event.events.network.ClientDisconnectedEvent;
import com.shepherdjerred.capstone.server.event.events.network.NetworkEvent;
import com.shepherdjerred.capstone.server.event.events.network.PacketReceivedEvent;
import com.shepherdjerred.capstone.server.network.Connection;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import java.util.concurrent.ConcurrentLinkedQueue;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * Forwards channel event as event in a queue.
 */
@Log4j2
@RequiredArgsConstructor
public class ServerChannelHandler extends ChannelInboundHandlerAdapter {

  private Connection connection;
  private ChannelHandlerContext context;
  private final ConcurrentLinkedQueue<NetworkEvent> eventQueue;

  @Override
  public void channelActive(ChannelHandlerContext context) {
    this.context = context;
    connection = new NettyConnection(this);
    eventQueue.add(new ClientConnectedEvent(connection));
  }

  @Override
  public void channelRead(ChannelHandlerContext context, Object message) {
    var packet = (Packet) message;
    var event = new PacketReceivedEvent(connection, packet);
    eventQueue.add(event);
  }

  @Override
  public void channelReadComplete(ChannelHandlerContext context) {
    context.writeAndFlush(Unpooled.EMPTY_BUFFER)
        .addListener(ChannelFutureListener.CLOSE);
  }

  @Override
  public void channelInactive(ChannelHandlerContext ctx) {
    eventQueue.add(new ClientDisconnectedEvent(connection));
  }

  public void send(Object object) {
    context.channel().writeAndFlush(object);
  }

  public void disconnect() {
    context.disconnect();
  }
}
