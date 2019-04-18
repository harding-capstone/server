package com.shepherdjerred.capstone.server.event.handlers;

import com.shepherdjerred.capstone.events.handlers.EventHandler;
import com.shepherdjerred.capstone.server.event.events.network.PacketReceivedEvent;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PacketReceivedEventHandler implements EventHandler<PacketReceivedEvent> {

  @Override
  public void handle(PacketReceivedEvent event) {

  }
}
