package com.shepherdjerred.capstone.server.network.connection;


import com.shepherdjerred.capstone.server.network.message.Message;
import java.util.UUID;

public interface Connection {

  UUID getUuid();

  void send(Message message);

  Message getNextMessage();

  void setConnectionStatus(ConnectionStatus connectionStatus);

  ConnectionStatus getConnectionStatus();

  void disconnect();

  boolean hasMessage();

}
