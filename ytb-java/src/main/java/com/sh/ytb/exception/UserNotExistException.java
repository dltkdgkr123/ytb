package com.sh.ytb.exception;

public class UserNotExistException extends RuntimeException {

  public UserNotExistException(String name) {
    super("This user does not exist: " + name);
  }
}
