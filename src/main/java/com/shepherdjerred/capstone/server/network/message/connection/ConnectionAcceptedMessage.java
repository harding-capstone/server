package com.shepherdjerred.capstone.server.network.message.connection;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class ConnectionAcceptedMessage {

  private final UUID playerUuid;
}
