package com.shepherdjerred.capstone.server.server.clients;

import com.shepherdjerred.capstone.server.packets.Packet;

public interface ClientConnection {

  void sendPacket(Packet packet);

  void disconnect();
}
