package com.shepherdjerred.capstone.server.packet.serialization;

import com.google.common.base.Charsets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;
import com.shepherdjerred.capstone.server.packet.packets.ConnectionAcceptedPacket;
import com.shepherdjerred.capstone.server.packet.packets.Packet;
import com.shepherdjerred.capstone.server.packet.packets.PlayerDescriptionPacket;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class PacketJsonSerializer implements PacketSerializer {

  private Gson gson;

  public PacketJsonSerializer() {
    var packetTypeFactory = RuntimeTypeAdapterFactory.of(Packet.class, "packetType")
        .registerSubtype(ConnectionAcceptedPacket.class)
        .registerSubtype(PlayerDescriptionPacket.class);

    gson = new GsonBuilder()
        .enableComplexMapKeySerialization()
        .registerTypeAdapterFactory(packetTypeFactory)
        .create();
  }

  @Override
  public byte[] toBytes(Packet packet) {
    var packetAsJson = gson.toJson(packet, Packet.class);
    return packetAsJson.getBytes(Charsets.UTF_16);
  }

  @Override
  public Packet fromBytes(byte[] packetAsBytes) {
    var packetAsString = new String(packetAsBytes, Charsets.UTF_16);
    return gson.fromJson(packetAsString, new TypeToken<Packet>(){}.getType());
  }
}
