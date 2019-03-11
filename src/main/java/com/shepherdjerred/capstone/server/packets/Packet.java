package com.shepherdjerred.capstone.server.packets;

public interface Packet {

  Identifier getIdentifier();

  enum Identifier {
    CONNECTION_ACCEPTED,
    CONNECTION_REJECTED,
    PLAYER_DESCRIPTION
  }
}
