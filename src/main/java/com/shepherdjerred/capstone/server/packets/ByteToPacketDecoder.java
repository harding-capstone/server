package com.shepherdjerred.capstone.server.packets;

import com.shepherdjerred.capstone.server.packets.serialization.PacketJsonSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ByteToPacketDecoder extends ByteToMessageDecoder {

  private final PacketJsonSerializer serializer;

  @Override
  protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
    out.add(serializer.fromBytes(in.array()));
  }
}
