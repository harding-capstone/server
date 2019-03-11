package com.shepherdjerred.capstone.server.packets;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class ConnectionAcceptedPacket implements Packet {

  private final Identifier identifier = Identifier.CONNECTION_ACCEPTED;
}
