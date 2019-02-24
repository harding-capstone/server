package com.shepherdjerred.capstone.server.network.message;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class PlayerInfoMessage implements Message {

  private final String playerName;
}
