package com.shepherdjerred.capstone.server.network;


import com.shepherdjerred.capstone.network.packet.packets.Packet;

public interface ClientConnection {

  void sendPacket(Packet packet);

  void disconnect();
}
