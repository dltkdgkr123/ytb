package com.sh.ytb.controller;

import com.sh.ytb.service.OAuthService;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.GeneralSecurityException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/oauth")
@RequiredArgsConstructor
class OAuthController {

  private final OAuthService oAuthService;

  @GetMapping("/google-auth")
  ResponseEntity<String> getGoogleAuthorizationUri() throws GeneralSecurityException, IOException {

    String uri = oAuthService.googleAuthorizationUriGet();

    return ResponseEntity.status(HttpStatus.OK).body(uri);
  }

  /* it's google auth endpoint */
  @GetMapping("/call-back")
  ResponseEntity<String> callbackAfterUserGoogleAuthSuccess(@RequestParam String code) {

    oAuthService.afterUserGoogleAuthSuccessCallback(code);
    /* TODO: 리디렉션 검증 */
    return ResponseEntity.status(HttpStatus.OK).body("call-back");
  }
}