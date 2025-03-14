package com.sh.ytb.app.service;

import com.google.api.client.auth.oauth2.Credential;
import com.sh.ytb.adapter.out.google.GoogleOAuthHelper;
import com.sh.ytb.adapter.out.jpa.entity.GoogleTokenJPAEntity;
import com.sh.ytb.adapter.out.jpa.entity.UserJPAEntity;
import com.sh.ytb.adapter.out.jpa.repo.UserRepository;
import com.sh.ytb.app.mapper.TokenMapper;
import com.sh.ytb.common.config.spec.TokenCipher;
import java.io.IOException;
import java.security.GeneralSecurityException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OAuthService {

  private final UserRepository userRepository;
  private final GoogleOAuthHelper googleOAuthHelper;
  private final TokenMapper tokenMapper;
  private final TokenCipher tokenCipher;

  public String googleAuthorizationUriGet() throws GeneralSecurityException, IOException {

    return googleOAuthHelper.getAuthorizationUri();
  }

  @Transactional
  public void afterUserGoogleAuthSuccessCallback(String code)
      throws GeneralSecurityException, IOException {

    Credential credential = googleOAuthHelper.afterCallback(code);

//    UserJPAEntity userJPAEntity = userRepository.findByUserId();
//
//    GoogleTokenJPAEntity googleTokenJPAEntity =
//        tokenMapper.mapEncryptedGoogleTokenToJPAEntity(
//            userJPAEntity,
//            tokenCipher.encrypt(credential.getAccessToken()),
//            tokenCipher.encrypt(credential.getRefreshToken())
//        );

    /* TODO: credential TokenEntity로 맵핑 후 DB 저장, 필요하다면 세션에 토큰 캐싱 */
  }
}
