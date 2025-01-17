package com.sh.ytb.service;

import com.google.api.client.auth.oauth2.StoredCredential;
import com.sh.ytb.adapter.OAuthHelper;
import com.sh.ytb.mapper.TokenMapper;
import com.sh.ytb.repository.GoogleTokenRepository;
import java.io.IOException;
import java.security.GeneralSecurityException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuthService {

  private final OAuthHelper oAuthHelper;
  private final GoogleTokenRepository googleTokenRepository;
  private final TokenMapper tokenMapper;
  private final PasswordEncoder tokenEncoder;


  public String authorizationUriGet() throws GeneralSecurityException, IOException {

    return oAuthHelper.getAuthorizationUri();
  }

  public boolean tokenStore() throws IOException {

    StoredCredential storedCredential = oAuthHelper.loadStoredCredential();

    googleTokenRepository.save(
        tokenMapper.mapToGoogleTokenJPAEntity(
            storedCredential.getAccessToken(),
            storedCredential.getRefreshToken()));

    return true;
  }
}
