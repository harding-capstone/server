package com.shepherdjerred.capstone.server.network.connection;


import com.shepherdjerred.capstone.server.network.packet.Packet;
import java.util.UUID;

public interface Connection {

  UUID getUuid();

  void send(Packet packet);

  Packet getNextMessage();

  void setConnectionStatus(ConnectionStatus connectionStatus);

  ConnectionStatus getConnectionStatus();

  void disconnect();

  boolean hasMessage();

}
