package com.shepherdjerred.capstone.server;

import com.shepherdjerred.capstone.common.lobby.LobbySettings;
import com.shepherdjerred.capstone.common.lobby.LobbySettings.LobbyType;
import com.shepherdjerred.capstone.logic.board.BoardSettings;
import com.shepherdjerred.capstone.logic.match.MatchSettings;
import com.shepherdjerred.capstone.logic.player.PlayerCount;
import com.shepherdjerred.capstone.logic.player.QuoridorPlayer;
import com.shepherdjerred.capstone.server.client.Client;
import com.shepherdjerred.capstone.server.network.netty.NettyServerConnector;
import com.shepherdjerred.capstone.server.network.netty.NettyServerSettings;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;

@Log4j2
public class ServerTest {

  @Test
  public void doTest() throws InterruptedException {
    var lobbySettings = new LobbySettings("Test",
        new MatchSettings(10, QuoridorPlayer.ONE, PlayerCount.TWO),
        new BoardSettings(9, PlayerCount.TWO),
        LobbyType.LOCAL,
        false);
    var server = new GameServer();
    var client = new Client();

    new Thread(() -> {
      server.registerConnector(new NettyServerConnector(new NettyServerSettings("127.0.0.1",
          35567)));
      server.broadcast();
      server.run();
    }).run();

//    client.start();
  }
}
