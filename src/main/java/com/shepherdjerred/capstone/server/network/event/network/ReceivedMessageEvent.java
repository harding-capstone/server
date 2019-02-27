package com.shepherdjerred.capstone.server.network.event.network;

import com.shepherdjerred.capstone.common.player.Player;
import com.shepherdjerred.capstone.server.network.connection.Connection;
import com.shepherdjerred.capstone.server.network.packet.Packet;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class ReceivedMessageEvent implements NetworkEvent {

  private final Connection connection;
  private final Player player;
  private final Packet packet;
}
