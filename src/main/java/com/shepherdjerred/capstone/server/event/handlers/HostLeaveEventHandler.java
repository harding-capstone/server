package com.shepherdjerred.capstone.server.event.handlers;

import com.shepherdjerred.capstone.events.handlers.EventHandler;
import com.shepherdjerred.capstone.network.packet.packets.LobbyAction;
import com.shepherdjerred.capstone.network.packet.packets.PlayerLobbyActionPacket;
import com.shepherdjerred.capstone.server.GameServer;
import com.shepherdjerred.capstone.server.event.events.HostLeaveEvent;
import com.shepherdjerred.capstone.server.network.ConnectorHub;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class HostLeaveEventHandler implements EventHandler<HostLeaveEvent> {
  private final GameServer gameServer;
  private final ConnectorHub connectorHub;

  @Override
  public void handle(HostLeaveEvent hostLeaveEvent) {
    connectorHub.sendPacket(new PlayerLobbyActionPacket(hostLeaveEvent.getPlayer(), LobbyAction.HOSTLEAVE) {
    });
  }

}
