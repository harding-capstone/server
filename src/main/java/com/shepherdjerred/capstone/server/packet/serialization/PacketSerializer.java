package com.shepherdjerred.capstone.server.packet.serialization;

import com.shepherdjerred.capstone.server.packet.packets.Packet;

public interface PacketSerializer {

  byte[] toBytes(Packet packet);

  Packet fromBytes(byte[] packetAsBytes);
}
