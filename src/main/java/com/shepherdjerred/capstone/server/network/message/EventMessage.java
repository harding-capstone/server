package com.shepherdjerred.capstone.server.network.message;

import com.shepherdjerred.capstone.server.events.Event;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class EventMessage {
  private final Event event;
}
