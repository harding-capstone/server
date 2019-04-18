package com.shepherdjerred.capstone.server.network.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;

public class NettyServerBroadcastHandler extends SimpleChannelInboundHandler<DatagramPacket> {

  @Override
  public void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
      ctx.close();
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
    cause.printStackTrace();
    ctx.close();
  }
}
