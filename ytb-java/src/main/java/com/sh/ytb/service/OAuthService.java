package com.sh.ytb.service;

import com.google.api.client.auth.oauth2.Credential;
import com.sh.ytb.adapter.GoogleOAuthHelper;
import java.io.IOException;
import java.security.GeneralSecurityException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OAuthService {

  private final GoogleOAuthHelper googleOAuthHelper;

  public String googleAuthorizationUriGet() throws GeneralSecurityException, IOException {

    return googleOAuthHelper.getAuthorizationUri();
  }

  @Transactional
  public void afterUserGoogleAuthSuccessCallback(String code) throws GeneralSecurityException, IOException {

    Credential credential = googleOAuthHelper.afterCallback(code);

    /* TODO: credential TokenEntity로 맵핑 후 DB 저장, 필요하다면 세션에 토큰 캐싱 */
  }
}
