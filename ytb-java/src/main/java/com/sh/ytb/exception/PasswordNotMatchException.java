package com.sh.ytb.exception;

public class PasswordNotMatchException extends RuntimeException  {

  public PasswordNotMatchException(String name) {
    super("Password does not match for user: " + name);
  }
}
