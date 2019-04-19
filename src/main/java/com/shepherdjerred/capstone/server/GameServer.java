package com.shepherdjerred.capstone.server;

import com.shepherdjerred.capstone.common.GameState;
import com.shepherdjerred.capstone.events.Event;
import com.shepherdjerred.capstone.events.EventBus;
import com.shepherdjerred.capstone.events.handlers.EventLoggerHandler;
import com.shepherdjerred.capstone.server.game.GameLogic;
import com.shepherdjerred.capstone.server.network.manager.NetworkManager;
import com.shepherdjerred.capstone.server.network.manager.events.StartNetworkEvent;
import java.net.SocketAddress;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;

@Log4j2
@ToString
public class GameServer implements Runnable {

  private final EventBus<Event> eventBus;
  private final NetworkManager networkManager;
  private final GameLogic gameLogic;
  private boolean shouldContinue;

  public GameServer(GameState gameState,
      SocketAddress gameAddress,
      SocketAddress broadcastAddress) {
    eventBus = new EventBus<>();
    gameLogic = new GameLogic(gameState, eventBus);
    networkManager = new NetworkManager(gameAddress, broadcastAddress, eventBus);
    shouldContinue = true;

    registerEventHandlers();
  }

  private void registerEventHandlers() {
    eventBus.registerHandler(new EventLoggerHandler<>());
  }

  @Override
  public void run() {
    final int ticksPerSecond = 30;
    final int millisecondsPerSecond = 1000;
    final int sleepMilliseconds = millisecondsPerSecond / ticksPerSecond;

    eventBus.dispatch(new StartNetworkEvent());

    while (shouldContinue) {
      networkManager.update();
      try {
        Thread.sleep(sleepMilliseconds);
      } catch (InterruptedException e) {
        log.error(e);
      }
    }
  }
}
