package com.shepherdjerred.capstone.server.network.event.handlers;

import com.shepherdjerred.capstone.common.player.HumanPlayer;
import com.shepherdjerred.capstone.events.Event;
import com.shepherdjerred.capstone.events.EventBus;
import com.shepherdjerred.capstone.events.handlers.EventHandler;
import com.shepherdjerred.capstone.network.packet.packets.PlayerDescriptionPacket;
import com.shepherdjerred.capstone.server.network.event.events.PacketReceivedEvent;
import com.shepherdjerred.capstone.server.event.PlayerInformationReceivedEvent;
import com.shepherdjerred.capstone.server.network.server.Connection;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@AllArgsConstructor
public class PacketReceivedEventHandler implements EventHandler<PacketReceivedEvent> {

  private final EventBus<Event> eventBus;

  @Override
  public void handle(PacketReceivedEvent event) {
    var packet = event.getPacket();

    if (packet instanceof PlayerDescriptionPacket) {
      handlePlayerDescriptionPacket((PlayerDescriptionPacket) packet, event.getConnection());
    }
  }

  private void handlePlayerDescriptionPacket(PlayerDescriptionPacket packet,
      Connection connection) {
    // TODO
    eventBus.dispatch(new PlayerInformationReceivedEvent(new HumanPlayer(packet.getPlayerInformation().getUuid(),
        packet.getPlayerInformation().getName(),
        null), connection));
  }
}
