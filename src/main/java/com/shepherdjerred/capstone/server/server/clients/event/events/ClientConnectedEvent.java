package com.shepherdjerred.capstone.server.server.clients.event.events;

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
}
