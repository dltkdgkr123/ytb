package com.sh.ytb.common.exception;

import java.io.IOException;
import java.security.GeneralSecurityException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

  /* 401 */
  @ExceptionHandler(CredentialNotFoundException.class)
  public ResponseEntity<String> handleCredentialNotFoundException(CredentialNotFoundException e) {
    return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(GeneralSecurityException.class)
  public ResponseEntity<String> handleGeneralSecurityException(GeneralSecurityException e) {
    return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
  }

  /* 403 */
  @ExceptionHandler(SessionNotExistException.class)
  public ResponseEntity<String> handleSessionNotExistException(SessionNotExistException e) {
    return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler(SessionInvalidException.class)
  public ResponseEntity<String> handleSessionInvalidException(SessionInvalidException e) {
    return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
  }


  /* 404 */
  @ExceptionHandler(UserNotExistException.class)
  public ResponseEntity<String> handleUserNotExistException(UserNotExistException e) {
    return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(PasswordNotMatchException.class)
  public ResponseEntity<String> handlePasswordNotMatchException(PasswordNotMatchException e) {
    return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
  }

  /* 500 */
  @ExceptionHandler(IOException.class)
  public ResponseEntity<String> handleIOException(IOException e) {
    return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(IllegalStateException.class)
  public ResponseEntity<String> handleIllegalStateException(IllegalStateException e) {
    return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(AESCipherInvalidException.class)
  public ResponseEntity<String> handleAESCipherInvalidException(AESCipherInvalidException e) {
    return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(SessionSerializerInvalidException.class)
  public ResponseEntity<String> handleSessionSerializerInvalidException(
      SessionSerializerInvalidException e) {
    return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
