package com.sh.ytb.common.exception;


/* case: session exists but is invalid */
public class SessionInvalidException extends RuntimeException {

  public SessionInvalidException() {
    super("Session is invalid");
  }
}

