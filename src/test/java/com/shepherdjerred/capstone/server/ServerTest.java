package com.shepherdjerred.capstone.server;

import com.shepherdjerred.capstone.server.client.Client;
import com.shepherdjerred.capstone.server.network.netty.NettyServerConnector;
import com.shepherdjerred.capstone.server.network.netty.NettyServerSettings;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;

@Log4j2
public class ServerTest {

//  @Test
//  public void doTest() throws InterruptedException {
//    var server = new GameServer();
//    var client = new Client();
//
//    new Thread(() -> {
//      try {
//        server.registerConnector(new NettyServerConnector(new NettyServerSettings("127.0.0.1", 9999)));
//        server.run();
//      } catch (InterruptedException e) {
//        e.printStackTrace();
//      }
//    }).start();
//
//    client.start();
//
//    while (true) {
//
//    }
//  }
}
