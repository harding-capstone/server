package com.shepherdjerred.capstone.server.client;

import com.shepherdjerred.capstone.common.chat.ChatMessage;
import com.shepherdjerred.capstone.network.packet.packets.PlayerDescriptionPacket;
import com.shepherdjerred.capstone.network.packet.packets.SendChatMessagePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import java.time.Instant;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class ClientHandler extends SimpleChannelInboundHandler {

  @Override
  public void channelActive(ChannelHandlerContext channelHandlerContext) {
    channelHandlerContext.writeAndFlush(new PlayerDescriptionPacket("Jerred"));
    var chatMessage = new ChatMessage(null, "Hey there!", Instant.now());
    channelHandlerContext.writeAndFlush(new SendChatMessagePacket(chatMessage));
  }

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, Object msg) {
    log.info("Received message: " + msg);
  }
}
