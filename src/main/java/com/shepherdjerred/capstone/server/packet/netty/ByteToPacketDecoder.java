package com.shepherdjerred.capstone.server.packet.netty;

import com.shepherdjerred.capstone.server.packet.serialization.PacketJsonSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@AllArgsConstructor
public class ByteToPacketDecoder extends ByteToMessageDecoder {

  private final PacketJsonSerializer serializer;

  @Override
  protected void decode(ChannelHandlerContext context, ByteBuf packetAsByteBuf, List<Object> out) {
    int numberOfBytes = packetAsByteBuf.readableBytes();
    byte[] readBytes = new byte[numberOfBytes - 4];
    packetAsByteBuf.skipBytes(4);
    packetAsByteBuf.readBytes(readBytes);
    var packet = serializer.fromBytes(readBytes);
    out.add(packet);
  }
}
