package com.shepherdjerred.capstone.server;

import com.shepherdjerred.capstone.server.client.Client;
import com.shepherdjerred.capstone.server.server.GameServer;
import com.shepherdjerred.capstone.server.server.clients.netty.NettyTcpConnector;
import com.shepherdjerred.capstone.server.server.clients.netty.NettySettings;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;

@Log4j2
public class ServerTest {

  @Test
  public void doTest() throws InterruptedException {
    var server = new GameServer();
    var client = new Client();

    new Thread(() -> {
      try {
        server.registerConnector(new NettyTcpConnector(new NettySettings("127.0.0.1", 9999)));
        server.run();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }).start();

    client.start();
  }
}
