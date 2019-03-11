package com.shepherdjerred.capstone.server.server.clients.netty;

import com.shepherdjerred.capstone.server.packets.Packet;
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
public class TcpServerHandler extends ChannelInboundHandlerAdapter {

  private ChannelHandlerContext context;
  private final ClientHandle clientHandle;
  private final ConcurrentLinkedQueue<ConnectorEvent> eventQueue;

  @Override
  public void channelActive(ChannelHandlerContext ctx) {
    context = ctx;
    eventQueue.add(new ClientConnectedEvent(clientHandle, new NettyTcpConnection(this)));
  }

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) {
    var packet = (Packet) msg;
    log.info("Server received " + packet);
    // TODO parse packet, etc.
    eventQueue.add(new ClientPacketReceivedEvent(clientHandle, packet));
  }

  @Override
  public void channelReadComplete(ChannelHandlerContext ctx) {
    ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)
        .addListener(ChannelFutureListener.CLOSE);
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
    cause.printStackTrace();
    ctx.close();
  }

  public void send(Object object) {
    log.info("Sending " + object);
    context.write(object);
  }

  public void disconnect() {
    context.disconnect();
  }
}
