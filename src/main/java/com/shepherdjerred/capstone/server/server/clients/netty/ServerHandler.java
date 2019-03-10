package com.shepherdjerred.capstone.server.server.clients.netty;

import com.shepherdjerred.capstone.server.server.clients.ClientHandle;
import com.shepherdjerred.capstone.server.server.clients.event.events.ClientConnectedEvent;
import com.shepherdjerred.capstone.server.server.clients.event.events.ClientPacketReceivedEvent;
import com.shepherdjerred.capstone.server.server.clients.event.events.ConnectorEvent;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import java.util.Queue;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ServerHandler extends ChannelInboundHandlerAdapter {

  private final ClientHandle clientHandle;
  private final Queue<ConnectorEvent> eventQueue;

  @Override
  public void channelActive(ChannelHandlerContext ctx) {
    eventQueue.add(new ClientConnectedEvent(clientHandle));
  }

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) {
    ByteBuf inBuffer = (ByteBuf) msg;

    String received = inBuffer.toString(CharsetUtil.UTF_8);
    System.out.println("NettyServer received: " + received);

    ctx.write(Unpooled.copiedBuffer("Hello " + received, CharsetUtil.UTF_8));
    // TODO parse packet, etc.
    eventQueue.add(new ClientPacketReceivedEvent(clientHandle, null));
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
}