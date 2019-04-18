package com.shepherdjerred.capstone.server.event.events;

import com.shepherdjerred.capstone.common.lobby.LobbySettings;
import com.shepherdjerred.capstone.events.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class LobbySettingsChangedEvent implements Event {
  private final LobbySettings newLobbySettings;
}
