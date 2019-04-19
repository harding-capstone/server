package com.shepherdjerred.capstone.server.game.state;

import com.shepherdjerred.capstone.common.lobby.LobbySettings.LobbyType;
import com.shepherdjerred.capstone.common.player.Element;
import com.shepherdjerred.capstone.common.player.HumanPlayer;
import com.shepherdjerred.capstone.events.Event;
import com.shepherdjerred.capstone.events.EventBus;
import com.shepherdjerred.capstone.events.handlers.EventHandlerFrame;
import com.shepherdjerred.capstone.server.event.PlayerInformationReceivedEvent;
import com.shepherdjerred.capstone.server.game.GameLogic;
import com.shepherdjerred.capstone.server.network.manager.events.StartBroadcastEvent;

public class PreLobbyState extends AbstractGameServerState {

  public PreLobbyState(GameLogic gameLogic,
      EventBus<Event> eventBus) {
    super(gameLogic, eventBus);
  }

  @Override
  protected EventHandlerFrame<Event> createEventHandlerFrame() {
    var frame = new EventHandlerFrame<>();

    frame.registerHandler(PlayerInformationReceivedEvent.class, event -> {
      var playerInformation = event.getPlayerInformation();
      var player = new HumanPlayer(playerInformation.getUuid(),
          playerInformation.getName(),
          Element.ICE);

      gameLogic.setHost(player);
      gameLogic.transitionState(new LobbyState(gameLogic, eventBus));

      var lobbyType = gameLogic.getGameState().getLobby().getLobbySettings().getLobbyType();

      if (lobbyType == LobbyType.NETWORK) {
        eventBus.dispatch(new StartBroadcastEvent());
      }
    });

    return frame;
  }

}
