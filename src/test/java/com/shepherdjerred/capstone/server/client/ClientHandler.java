package com.shepherdjerred.capstone.server.client;

import com.shepherdjerred.capstone.server.packets.PlayerDescriptionPacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class ClientHandler extends SimpleChannelInboundHandler {

  @Override
  public void channelActive(ChannelHandlerContext channelHandlerContext) {
    channelHandlerContext.writeAndFlush(new PlayerDescriptionPacket("Jerred"));
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable cause) {
    cause.printStackTrace();
    channelHandlerContext.close();
  }

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, Object msg) {
    log.info("Received" + msg);
  }
}
