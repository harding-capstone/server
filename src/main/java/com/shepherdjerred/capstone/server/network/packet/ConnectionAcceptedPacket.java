package com.shepherdjerred.capstone.server.network.packet;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class ConnectionAcceptedPacket implements Packet {

  private final UUID playerUuid;
}
