package com.shepherdjerred.capstone.server.game.state;

import com.shepherdjerred.capstone.common.player.HumanPlayer;
import com.shepherdjerred.capstone.events.Event;
import com.shepherdjerred.capstone.events.EventBus;
import com.shepherdjerred.capstone.events.handlers.EventHandlerFrame;
import com.shepherdjerred.capstone.server.event.PlayerInformationReceivedEvent;
import com.shepherdjerred.capstone.server.event.PlayerJoinEvent;
import com.shepherdjerred.capstone.server.game.GameLogic;

public class LobbyState extends AbstractGameServerState {

  public LobbyState(GameLogic gameLogic,
      EventBus<Event> eventBus) {
    super(gameLogic, eventBus);
  }

  @Override
  protected EventHandlerFrame<Event> createEventHandlerFrame() {
    var frame = new EventHandlerFrame<>();

    frame.registerHandler(PlayerInformationReceivedEvent.class, (event) -> {
      var lobby = gameLogic.getGameState().getLobby();

      if (lobby.isFull()) {
        // TODO Send a message saying the server is full
        event.getConnection().sendPacket(null);
        event.getConnection().disconnect();
      }

      var element = lobby.getFreeElement();

      if (element.isEmpty()) {
        throw new IllegalStateException("No free element");
      }

      var playerInformation = event.getPlayerInformation();
      var player = new HumanPlayer(playerInformation.getUuid(),
          playerInformation.getName(),
          element.get());

      eventBus.dispatch(new PlayerJoinEvent(player, event.getConnection()));
    });

    return frame;
  }
}
