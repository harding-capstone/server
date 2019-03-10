package com.shepherdjerred.capstone.server.server.clients.netty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class NettySettings {

  private final String hostname;
  private final int port;
}
