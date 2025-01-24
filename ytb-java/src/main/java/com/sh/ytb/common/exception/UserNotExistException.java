package com.sh.ytb.common.exception;

public class UserNotExistException extends RuntimeException {

  public UserNotExistException(String userId) {
    super("This user does not exist: " + userId);
  }

  public UserNotExistException(Long id) {
    super("This user does not exist: " + id);
  }
}
