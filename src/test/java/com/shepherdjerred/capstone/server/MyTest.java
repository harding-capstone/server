package com.shepherdjerred.capstone.server;

import com.shepherdjerred.capstone.common.chat.ChatHistory;
import com.shepherdjerred.capstone.common.lobby.Lobby;
import com.shepherdjerred.capstone.common.lobby.LobbySettings;
import com.shepherdjerred.capstone.common.lobby.LobbySettings.LobbyType;
import com.shepherdjerred.capstone.logic.board.Board;
import com.shepherdjerred.capstone.logic.board.BoardPieces;
import com.shepherdjerred.capstone.logic.board.BoardPiecesInitializer;
import com.shepherdjerred.capstone.logic.board.BoardSettings;
import com.shepherdjerred.capstone.logic.board.layout.BoardCellsInitializer;
import com.shepherdjerred.capstone.logic.board.layout.BoardLayout;
import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.match.MatchSettings;
import com.shepherdjerred.capstone.logic.match.MatchSettings.PlayerCount;
import com.shepherdjerred.capstone.logic.player.PlayerId;
import com.shepherdjerred.capstone.server.network.connection.local.LocalConnectionBridge;
import com.shepherdjerred.capstone.server.network.packet.PlayerInitializationPacket;
import com.shepherdjerred.capstone.server.server.GameServer;
import com.shepherdjerred.capstone.server.server.ServerSettings;
import org.junit.Test;

public class MyTest {

  @Test
  public void doTest() throws InterruptedException {
    BoardSettings boardSettings = new BoardSettings(9, PlayerCount.TWO);
    MatchSettings matchSettings = new MatchSettings(10, PlayerId.ONE, boardSettings);
    BoardCellsInitializer boardCellsInitializer = new BoardCellsInitializer();
    BoardLayout boardLayout = BoardLayout.fromBoardSettings(boardCellsInitializer, boardSettings);
    BoardPiecesInitializer boardPiecesInitializer = new BoardPiecesInitializer();
    BoardPieces boardPieces = BoardPieces.initializePieceLocations(boardSettings,
        boardPiecesInitializer);
    Board board = Board.createBoard(boardLayout, boardPieces);
    Match match = Match.startNewMatch(matchSettings, board);

    LobbySettings lobbySettings = new LobbySettings("My Lobby!",
        matchSettings,
        LobbyType.LOCAL,
        false);

    GameServer gameServer = new GameServer(new ServerSettings(),
        new ChatHistory(),
        match,
        Lobby.fromLobbySettings(lobbySettings));

    LocalConnectionBridge localConnectionBridge = new LocalConnectionBridge();

    gameServer.connectLocalClient(localConnectionBridge);

    new Thread(() -> {
      try {
        gameServer.run();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }).start();

    var infoMessage = new PlayerInitializationPacket("Jerred");
    localConnectionBridge.publishToServer(infoMessage);

    // TODO let the processing stop...
    Thread.sleep(1000);

    //    var chatEvent = new SendChatEvent(new ChatMessage(player, "Hello!!", Instant.now()));
//    gameServer.dispatchEvent(connectEvent);
//    gameServer.dispatchEvent(chatEvent);
  }
}
