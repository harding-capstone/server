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
public class ClientRejectedEvent implements ClientEvent {

  private final Client client;
  private final Reason reason;

  enum Reason {
    LOBBY_FULL, GAME_IN_PROGRESS
  }
}
