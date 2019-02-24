package com.shepherdjerred.capstone.server.network.local;

import com.shepherdjerred.capstone.server.network.Connection;
import com.shepherdjerred.capstone.server.network.ConnectionStatus;
import com.shepherdjerred.capstone.server.network.message.Message;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Represents a connection from a server to a local connection.
 */
@ToString
public class LocalConnection implements Connection {

  @Getter
  @Setter
  private ConnectionStatus connectionStatus;
  private final LocalConnectionBridge localConnectionBridge;

  public LocalConnection(LocalConnectionBridge localConnectionBridge) {
    this.localConnectionBridge = localConnectionBridge;
    this.connectionStatus = ConnectionStatus.DISCONNECTED;
  }

  @Override
  public void send(Message message) {
    localConnectionBridge.publishToClient(message);
  }

  @Override
  public Message getNextMessage() {
    return localConnectionBridge.getEventForServer();
  }

  @Override
  public void disconnect() {

  }

  @Override
  public boolean hasMessage() {
    return localConnectionBridge.hasEventForServer();
  }
}
