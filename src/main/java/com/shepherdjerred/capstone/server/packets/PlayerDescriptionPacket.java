package com.shepherdjerred.capstone.server.packets;

import com.shepherdjerred.capstone.server.packets.Packet.Identifier;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class PlayerDescriptionPacket {

  private final Identifier identifier = Identifier.PLAYER_DESCRIPTION;
  private final String name;
}
