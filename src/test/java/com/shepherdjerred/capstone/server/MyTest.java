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
import com.shepherdjerred.capstone.server.events.player.PlayerInfoEvent;
import com.shepherdjerred.capstone.server.network.local.LocalBridge;
import com.shepherdjerred.capstone.server.server.Server;
import com.shepherdjerred.capstone.server.server.ServerSettings;
import org.junit.Test;

public class MyTest {

  @Test
  public void doTest() {
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

    Server server = new Server(new ServerSettings(),
        new ChatHistory(),
        match,
        Lobby.fromLobbySettings(lobbySettings));

    LocalBridge localBridge = new LocalBridge();

    server.connectLocalClient(localBridge);

    server.loop();

    var infoEvent = new PlayerInfoEvent("My name!!");
    localBridge.publishToServer(infoEvent);

    server.loop();

    //    var chatEvent = new SendChatEvent(new ChatMessage(player, "Hello!!", Instant.now()));
//    server.dispatchEvent(connectEvent);
//    server.dispatchEvent(chatEvent);
  }
}
