package com.shepherdjerred.capstone.server;

import com.shepherdjerred.capstone.common.Constants;
import java.net.InetSocketAddress;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;

@Log4j2
public class ServerTest {

  @Test
  public void doTest() {
    var server = new GameServer(new InetSocketAddress(Constants.GAME_PORT),
        new InetSocketAddress(Constants.DISCOVERY_PORT));

    server.run();
  }
}
