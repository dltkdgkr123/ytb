package com.sh.ytb.controller;

import com.sh.ytb.dto.res.GoogleTokenResDTO;
import com.sh.ytb.dto.req.GoogleTokenLoadReqDTO;
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
  ResponseEntity<GoogleTokenResDTO> loadGoogleToken(@RequestBody GoogleTokenLoadReqDTO googleTokenLoadReqDTO) {

    // TODO: 본인 맞는지 검증 절차
    Optional<GoogleTokenResDTO> result = oAuthService.decryptedTokenLoad(googleTokenLoadReqDTO);

    return result
        .map(googleTokenResDTO -> ResponseEntity.status(HttpStatus.OK).body(googleTokenResDTO))
        .orElseGet(() -> ResponseEntity.status(HttpStatus.FORBIDDEN).build());
  }
}
