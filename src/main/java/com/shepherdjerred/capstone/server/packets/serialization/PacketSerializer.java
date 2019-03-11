package com.shepherdjerred.capstone.server.packets.serialization;

import com.shepherdjerred.capstone.server.packets.Packet;

public interface PacketSerializer {

  byte[] toBytes(Packet packet);

  Packet fromBytes(byte[] packetAsBytes);
}
