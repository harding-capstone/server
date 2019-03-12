package com.shepherdjerred.capstone.server.packet.netty;

import com.shepherdjerred.capstone.server.packet.packets.Packet;
import com.shepherdjerred.capstone.server.packet.serialization.PacketJsonSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@AllArgsConstructor
public class PacketToByteEncoder extends MessageToByteEncoder<Packet> {

  private final PacketJsonSerializer serializer;

  @Override
  protected void encode(ChannelHandlerContext context, Packet packet, ByteBuf out) {
    var packetAsBytes = serializer.toBytes(packet);
    out.writeInt(packetAsBytes.length);
    out.writeBytes(packetAsBytes);
  }
}
