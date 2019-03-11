package com.shepherdjerred.capstone.server.packets;

import com.shepherdjerred.capstone.server.packets.serialization.PacketJsonSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PacketToByteEncoder extends MessageToByteEncoder<Packet> {

  private final PacketJsonSerializer serializer;

  @Override
  protected void encode(ChannelHandlerContext ctx, Packet msg, ByteBuf out) {
    out.writeBytes(serializer.toBytes(msg));
  }
}
