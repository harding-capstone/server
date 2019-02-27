package com.shepherdjerred.capstone.server.network.connection.local;

import com.shepherdjerred.capstone.server.network.connection.Connection;
import com.shepherdjerred.capstone.server.network.connection.ConnectionStatus;
import com.shepherdjerred.capstone.server.network.packet.Packet;
import java.util.UUID;
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
  @Getter
  private final UUID uuid;
  private final LocalConnectionBridge localConnectionBridge;

  public LocalConnection(UUID uuid, LocalConnectionBridge localConnectionBridge) {
    this.uuid = uuid;
    this.localConnectionBridge = localConnectionBridge;
    this.connectionStatus = ConnectionStatus.DISCONNECTED;
  }

  @Override
  public void send(Packet packet) {
    localConnectionBridge.publishToClient(packet);
  }

  @Override
  public Packet getNextMessage() {
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
