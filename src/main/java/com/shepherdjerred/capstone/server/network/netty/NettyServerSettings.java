package com.shepherdjerred.capstone.server.network.netty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class NettyServerSettings {

  private final String hostname;
  private final int port;
}
