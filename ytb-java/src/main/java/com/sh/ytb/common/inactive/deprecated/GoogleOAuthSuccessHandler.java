package com.sh.ytb.common.inactive.deprecated;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow.CredentialCreatedListener;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.sh.ytb.adapter.out.jpa.entity.UserJPAEntity;
import com.sh.ytb.app.mapper.TokenMapper;
import com.sh.ytb.adapter.out.jpa.repo.GoogleTokenRepository;
import com.sh.ytb.adapter.out.jpa.repo.UserRepository;
import com.sh.ytb.common.config.spec.TokenCipher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Deprecated
@Component
@RequiredArgsConstructor
public class GoogleOAuthSuccessHandler implements CredentialCreatedListener {

  private final GoogleTokenRepository googleTokenRepository;
  private final UserRepository userRepository;
  private final TokenMapper tokenMapper;
  private final TokenCipher tokenCipher;

  /* TODO: 세션 기반 Id 주입 */
  private final Long id;


  @Override
  public void onCredentialCreated(Credential credential, TokenResponse tokenResponse) {

    // OPTION: DB 거쳐서 user exist 비교

    googleTokenRepository.save(tokenMapper.mapEncryptedGoogleTokenToJPAEntity(
        UserJPAEntity.builder()
            .id(id)
            // exclude binding of other user properties
            .build(),
        tokenCipher.encrypt(credential.getAccessToken()),
        tokenCipher.encrypt(credential.getRefreshToken())));
  }
}
