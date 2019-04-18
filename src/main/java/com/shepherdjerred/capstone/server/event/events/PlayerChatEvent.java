package com.shepherdjerred.capstone.server.event.events;

import com.shepherdjerred.capstone.common.chat.ChatMessage;
import com.shepherdjerred.capstone.common.player.Player;
import com.shepherdjerred.capstone.events.Event;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class PlayerChatEvent implements Event {

  private final Player player;
  private final ChatMessage chatMessage;
}
