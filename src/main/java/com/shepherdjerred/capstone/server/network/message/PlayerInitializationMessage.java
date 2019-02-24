package com.shepherdjerred.capstone.server.network.message;

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
public class PlayerInitializationMessage implements Message {

  private final String playerName;
}
