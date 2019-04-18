package com.shepherdjerred.capstone.server.network.broadcast.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class NettyBroadcastHandler extends ChannelOutboundHandlerAdapter {

  @Override
  public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise)
      throws Exception {
    log.info("Calling write... " + msg);
    super.write(ctx, msg, promise);
  }
}
