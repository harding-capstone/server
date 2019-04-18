package com.shepherdjerred.capstone.server.event.events.network;

import com.shepherdjerred.capstone.network.packet.packets.Packet;
import com.shepherdjerred.capstone.server.network.ClientId;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class PacketReceivedEvent implements NetworkEvent {

  private final ClientId clientId;
  private final Packet packet;
}

