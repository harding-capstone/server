package com.shepherdjerred.capstone.server.event.events.network;

import com.shepherdjerred.capstone.server.network.Connection;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class ClientConnectedEvent implements NetworkEvent {

  private final Connection connection;
}
