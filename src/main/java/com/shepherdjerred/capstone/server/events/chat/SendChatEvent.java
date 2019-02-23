package com.shepherdjerred.capstone.server.events.chat;

import com.shepherdjerred.capstone.common.chat.ChatMessage;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class SendChatEvent implements ChatEvent {
  private final ChatMessage chatMessage;
}
