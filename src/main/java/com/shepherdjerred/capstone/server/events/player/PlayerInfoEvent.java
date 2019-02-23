package com.shepherdjerred.capstone.server.events.player;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class PlayerInfoEvent implements PlayerEvent {

  private final String playerName;
}
