package com.shepherdjerred.capstone.server.server.clients.netty;

import com.shepherdjerred.capstone.server.packet.packets.Packet;
import com.shepherdjerred.capstone.server.server.clients.ClientConnection;
import lombok.AllArgsConstructor;
import lombok.ToString;

@ToString(exclude = "handler")
@AllArgsConstructor
public class NettyTcpConnection implements ClientConnection {

  private final ChannelHandler handler;

  @Override
  public void sendPacket(Packet packet) {
    handler.send(packet);
  }

  @Override
  public void disconnect() {
    handler.disconnect();
  }
}
