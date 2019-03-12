package com.shepherdjerred.capstone.server.server.clients.netty;

import com.shepherdjerred.capstone.server.packet.packets.ConnectionAcceptedPacket;
import com.shepherdjerred.capstone.server.packet.packets.Packet;
import com.shepherdjerred.capstone.server.server.clients.ClientHandle;
import com.shepherdjerred.capstone.server.server.clients.events.ClientConnectedEvent;
import com.shepherdjerred.capstone.server.server.clients.events.ClientPacketReceivedEvent;
import com.shepherdjerred.capstone.server.server.clients.events.ConnectorEvent;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import java.util.concurrent.ConcurrentLinkedQueue;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
public class ChannelHandler extends ChannelInboundHandlerAdapter {

  private ChannelHandlerContext context;
  private final ClientHandle clientHandle;
  private final ConcurrentLinkedQueue<ConnectorEvent> eventQueue;

  @Override
  public void channelActive(ChannelHandlerContext context) {
    this.context = context;
    eventQueue.add(new ClientConnectedEvent(clientHandle, new NettyTcpConnection(this)));
    context.channel().writeAndFlush(new ConnectionAcceptedPacket());
  }

  @Override
  public void channelRead(ChannelHandlerContext context, Object message) {
    var packet = (Packet) message;
    eventQueue.add(new ClientPacketReceivedEvent(clientHandle, packet));
  }

  @Override
  public void channelReadComplete(ChannelHandlerContext context) {
    context.writeAndFlush(Unpooled.EMPTY_BUFFER)
        .addListener(ChannelFutureListener.CLOSE);
  }

  public void send(Object object) {
    context.channel().writeAndFlush(object);
  }

  public void disconnect() {
    context.disconnect();
  }
}
