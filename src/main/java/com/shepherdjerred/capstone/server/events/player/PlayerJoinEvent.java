package com.shepherdjerred.capstone.server.events.player;

import com.shepherdjerred.capstone.common.player.Player;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class PlayerJoinEvent implements PlayerEvent {
  private final Player player;
}
