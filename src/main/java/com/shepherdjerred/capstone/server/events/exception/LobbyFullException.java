package com.shepherdjerred.capstone.server.events.exception;

public class LobbyFullException extends Exception {
  public LobbyFullException() {
    super("Lobby is full");
  }
}
