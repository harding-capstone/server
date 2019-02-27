package com.shepherdjerred.capstone.server.network.packet;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Sent by a client to describe the player to the server.
 */
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class PlayerInitializationPacket implements Packet {

  private final String playerName;
}
