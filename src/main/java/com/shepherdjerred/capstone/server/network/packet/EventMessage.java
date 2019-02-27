package com.shepherdjerred.capstone.server.network.packet;

import com.shepherdjerred.capstone.server.events.Event;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class EventMessage<T extends Event> {
  private final T event;
}
