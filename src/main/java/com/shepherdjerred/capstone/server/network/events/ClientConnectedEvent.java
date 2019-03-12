package com.shepherdjerred.capstone.server.network.events;

import com.shepherdjerred.capstone.server.network.ClientConnection;
import com.shepherdjerred.capstone.server.network.ClientHandle;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class ClientConnectedEvent implements NetworkEvent {

  private final ClientHandle clientHandle;
  private final ClientConnection clientConnection;
}
