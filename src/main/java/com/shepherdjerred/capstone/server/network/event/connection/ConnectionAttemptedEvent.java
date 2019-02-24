package com.shepherdjerred.capstone.server.network.event.connection;

import com.shepherdjerred.capstone.server.network.connection.Connection;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class ConnectionAttemptedEvent implements ConnectionEvent {

  private final Connection connection;
}
