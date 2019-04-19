package com.shepherdjerred.capstone.server.network.server;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.shepherdjerred.capstone.common.player.Player;
import com.shepherdjerred.capstone.network.packet.packets.Packet;

public class NetworkServer {

  private final BiMap<Player, Connection> playerMap;

  public NetworkServer() {
    playerMap = HashBiMap.create();
  }

  public void setPlayerConnection(Player player, Connection connection) {
    playerMap.put(player, connection);
  }

  public Connection getConnection(Player player) {
    return playerMap.get(player);
  }

  public void send(Packet packet) {
    playerMap.values().forEach(connection -> connection.sendPacket(packet));
  }
}
