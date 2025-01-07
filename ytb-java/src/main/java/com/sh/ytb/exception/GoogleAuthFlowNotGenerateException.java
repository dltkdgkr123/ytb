package com.sh.ytb.exception;

public class GoogleAuthFlowNotGenerateException extends RuntimeException {

  public GoogleAuthFlowNotGenerateException() {
    super("Unable to create authentication flow");
  }
}
