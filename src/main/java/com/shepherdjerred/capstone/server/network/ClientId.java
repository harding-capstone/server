package com.shepherdjerred.capstone.server.network;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Identifier for a client connection.
 */
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class ClientId {

  private final UUID uuid;
}
