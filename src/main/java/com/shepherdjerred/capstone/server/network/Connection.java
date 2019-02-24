package com.shepherdjerred.capstone.server.network;


import com.shepherdjerred.capstone.server.network.message.Message;

public interface Connection {

  void send(Message message);

  Message getNextMessage();

  void setConnectionStatus(ConnectionStatus connectionStatus);

  ConnectionStatus getConnectionStatus();

  void disconnect();

  boolean hasMessage();

}
