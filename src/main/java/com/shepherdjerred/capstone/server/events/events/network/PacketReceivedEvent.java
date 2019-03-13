package com.shepherdjerred.capstone.server.events.events.network;

import com.shepherdjerred.capstone.network.packet.packets.Packet;
import com.shepherdjerred.capstone.server.network.Handle;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class PacketReceivedEvent implements NetworkEvent {

  private final Handle handle;
  private final Packet packet;
}

