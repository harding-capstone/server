package com.shepherdjerred.capstone.server.events.events.network;

import com.shepherdjerred.capstone.server.network.Connection;
import com.shepherdjerred.capstone.server.network.ClientId;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class ClientConnectedEvent implements NetworkEvent {

  private final ClientId clientId;
  private final Connection connection;
}
