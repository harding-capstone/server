package com.shepherdjerred.capstone.server.server.clients.events;

import com.shepherdjerred.capstone.server.server.clients.ClientConnection;
import com.shepherdjerred.capstone.server.server.clients.ClientHandle;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class ClientConnectedEvent implements ConnectorEvent {

  private final ClientHandle clientHandle;
  private final ClientConnection clientConnection;
}
