package com.shepherdjerred.capstone.server.events.network;

import com.shepherdjerred.capstone.server.events.Event;
import com.shepherdjerred.capstone.server.network.Client;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class ReceivedEventEvent implements NetworkEvent {

  private final Client client;
  private final Event event;
}
