package com.shepherdjerred.capstone.server.events.handlers;

import com.shepherdjerred.capstone.events.handlers.EventHandler;
import com.shepherdjerred.capstone.network.packet.packets.TurnPacket;
import com.shepherdjerred.capstone.server.GameServer;
import com.shepherdjerred.capstone.server.events.events.TurnEvent;
import com.shepherdjerred.capstone.server.network.ConnectorHub;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TurnEventHandler implements EventHandler<TurnEvent> {
  private final GameServer gameServer;
  private final ConnectorHub connectorHub;

  @Override
  public void handle(TurnEvent turnEvent) {
    gameServer.MakeTurn(turnEvent.getTurn());
    connectorHub.sendPacket(new TurnPacket(turnEvent.getTurn()) {
      });
  }

}
