package com.sh.ytb.controller;

import com.sh.ytb.dto.GoogleTokenDTO;
import com.sh.ytb.dto.TokenLoadReqDTO;
import com.sh.ytb.service.OAuthService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class OAuthController {

  private final OAuthService oAuthService;

  @GetMapping("/load-google-token")
  ResponseEntity<GoogleTokenDTO> loadGoogleToken(@RequestBody TokenLoadReqDTO tokenLoadReqDTO) {

    // TODO: 본인 맞는지 검증 절차
    Optional<GoogleTokenDTO> result = oAuthService.tokenLoad(tokenLoadReqDTO);

    return result
        .map(googleTokenDTO -> ResponseEntity.status(HttpStatus.OK).body(googleTokenDTO))
        .orElseGet(() -> ResponseEntity.status(HttpStatus.FORBIDDEN).build());
  }
}
