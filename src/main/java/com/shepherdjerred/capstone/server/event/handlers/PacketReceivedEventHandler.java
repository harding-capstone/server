package com.shepherdjerred.capstone.server.event.handlers;

import com.shepherdjerred.capstone.common.player.HumanPlayer;
import com.shepherdjerred.capstone.events.Event;
import com.shepherdjerred.capstone.events.EventBus;
import com.shepherdjerred.capstone.events.handlers.EventHandler;
import com.shepherdjerred.capstone.network.packet.packets.PlayerDescriptionPacket;
import com.shepherdjerred.capstone.server.event.events.network.PacketReceivedEvent;
import com.shepherdjerred.capstone.server.event.events.network.PlayerJoinEvent;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@AllArgsConstructor
public class PacketReceivedEventHandler implements EventHandler<PacketReceivedEvent> {

  private final EventBus<Event> eventBus;

  @Override
  public void handle(PacketReceivedEvent event) {
    var packet = event.getPacket();

    log.info("Received a packet.");

    if (packet instanceof PlayerDescriptionPacket) {
      handlePlayerDescriptionPacket((PlayerDescriptionPacket) packet);
    }
  }

  private void handlePlayerDescriptionPacket(PlayerDescriptionPacket packet) {
    // TODO
    eventBus.dispatch(new PlayerJoinEvent(new HumanPlayer(packet.getPlayerInformation().getUuid(),
        packet.getPlayerInformation().getName(),
        null)));
  }
}
