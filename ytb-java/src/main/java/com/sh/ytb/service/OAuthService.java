package com.sh.ytb.service;

import com.sh.ytb.adapter.OAuthHelper;
import java.io.IOException;
import java.security.GeneralSecurityException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuthService {

  private final OAuthHelper oAuthHelper;

  public String authorizationUriGet() throws GeneralSecurityException, IOException {

    return oAuthHelper.getAuthorizationUri();
  }

  public String credentialStore() {

    // TODO: 세션 기반 유저 get 및 토큰 저장

    return "";
  }
}
