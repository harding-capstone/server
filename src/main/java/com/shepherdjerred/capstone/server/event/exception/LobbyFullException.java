package com.shepherdjerred.capstone.server.event.exception;

public class LobbyFullException extends Exception {
  public LobbyFullException() {
    super("Lobby is full");
  }
}
