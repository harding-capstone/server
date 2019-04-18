package com.shepherdjerred.capstone.server.network.broadcast.netty;

import com.shepherdjerred.capstone.network.netty.PacketCodec;
import com.shepherdjerred.capstone.network.packet.serialization.PacketJsonSerializer;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LoggingHandler;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BroadcastChannelInitializer extends ChannelInitializer<NioDatagramChannel> {

  @Override
  protected void initChannel(NioDatagramChannel datagramChannel) {
    var pipeline = datagramChannel.pipeline();
    var serializer = new PacketJsonSerializer();

    pipeline.addLast(new LengthFieldBasedFrameDecoder(65535, 0, 4));
    pipeline.addLast(new PacketCodec(serializer));
    pipeline.addLast(new NettyBroadcastHandler());
    pipeline.addLast(new LoggingHandler());
  }
}
