package com.sh.ytb.common.exception;

public class CredentialNotFoundException extends RuntimeException {

  public CredentialNotFoundException(String path) {
    super("Could not find stored credential for absolute path: " + path);
  }

  public static CredentialNotFoundException fromAbsoluteFilePath(String absolutePath) {
    return new CredentialNotFoundException(absolutePath);
  }
}
