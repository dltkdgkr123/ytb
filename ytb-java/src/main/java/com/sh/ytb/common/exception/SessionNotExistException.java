package com.sh.ytb.common.exception;

public class SessionNotExistException extends RuntimeException {

  public SessionNotExistException() {
    super("Session does not exist");
  }
}

