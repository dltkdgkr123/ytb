package com.sh.ytb.adapter.in.web;

import com.sh.ytb.app.service.OAuthService;
import java.io.IOException;
import java.security.GeneralSecurityException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
  ResponseEntity<String> callbackAfterUserGoogleAuthSuccess(@RequestParam String code)
      throws GeneralSecurityException, IOException {

    oAuthService.afterUserGoogleAuthSuccessCallback(code);

    return ResponseEntity.status(HttpStatus.OK).build();
  }
}