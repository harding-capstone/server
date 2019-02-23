package com.shepherdjerred.capstone.server.events.client;

import com.shepherdjerred.capstone.server.network.Client;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class ClientAcceptedEvent implements ClientEvent {

  private final Client client;
}
