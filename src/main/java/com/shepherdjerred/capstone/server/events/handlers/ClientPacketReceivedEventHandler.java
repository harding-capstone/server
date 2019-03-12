package com.shepherdjerred.capstone.server.events.handlers;

import com.shepherdjerred.capstone.events.handlers.EventHandler;
import com.shepherdjerred.capstone.server.network.ClientConnectors;
import com.shepherdjerred.capstone.server.network.events.PacketReceivedEvent;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ClientPacketReceivedEventHandler implements EventHandler<PacketReceivedEvent> {

  private final ClientConnectors clientConnectors;

  @Override
  public void handle(PacketReceivedEvent event) {

  }
}
