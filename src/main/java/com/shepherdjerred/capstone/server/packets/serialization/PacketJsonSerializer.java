package com.shepherdjerred.capstone.server.packets.serialization;

import com.google.common.base.Charsets;
import com.google.gson.Gson;
import com.shepherdjerred.capstone.server.packets.Packet;

public class PacketJsonSerializer implements PacketSerializer {

  private Gson gson;

  public PacketJsonSerializer() {
    gson = new Gson();
  }

  @Override
  public byte[] toBytes(Packet packet) {
    return gson.toJson(packet).getBytes();
  }

  @Override
  public Packet fromBytes(byte[] packetAsBytes) {
    return gson.fromJson(new String(packetAsBytes, Charsets.UTF_8), Packet.class);
  }
}
