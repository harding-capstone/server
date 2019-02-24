package com.shepherdjerred.capstone.server.events.network;

import com.shepherdjerred.capstone.common.player.Player;
import com.shepherdjerred.capstone.server.network.message.Message;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class ReceivedMessageEvent implements NetworkEvent {

  private final Player player;
  private final Message message;
}
