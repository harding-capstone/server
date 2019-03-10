package com.shepherdjerred.capstone.server;

import com.shepherdjerred.capstone.server.client.Client;
import com.shepherdjerred.capstone.server.server.GameServer;
import com.shepherdjerred.capstone.server.server.clients.netty.NettyClientConnector;
import com.shepherdjerred.capstone.server.server.clients.netty.NettySettings;
import org.junit.Test;

public class ServerTest {

  @Test
  public void doTest() throws InterruptedException {
    var server = new GameServer();
    var client = new Client();

    new Thread(() -> {
      try {
        server.registerConnector(new NettyClientConnector(new NettySettings("127.0.0.1", 9999)));
        server.run();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }).start();

    Thread.sleep(2000);

    client.start();
  }
}
