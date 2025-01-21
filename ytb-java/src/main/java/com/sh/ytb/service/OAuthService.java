package com.sh.ytb.service;

import com.google.api.client.auth.oauth2.StoredCredential;
import com.sh.ytb.adapter.OAuthHelper;
import com.sh.ytb.dto.res.GoogleTokenResDTO;
import com.sh.ytb.dto.req.GoogleTokenLoadReqDTO;
import com.sh.ytb.entity.GoogleTokenJPAEntity;
import com.sh.ytb.exception.UserNotExistException;
import com.sh.ytb.mapper.TokenMapper;
import com.sh.ytb.repository.GoogleTokenRepository;
import com.sh.ytb.repository.UserRepository;
import com.sh.ytb.specs.TokenCipher;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuthService {

  private final OAuthHelper oAuthHelper;
  private final GoogleTokenRepository googleTokenRepository;
  private final UserRepository userRepository;
  private final TokenMapper tokenMapper;
  private final TokenCipher tokenCipher;


  public String authorizationUriGet() throws GeneralSecurityException, IOException {

    return oAuthHelper.getAuthorizationUri();
  }

  public void encryptedTokenStore() throws IOException {

    StoredCredential storedCredential = oAuthHelper.loadStoredCredential();

    googleTokenRepository.save(
        tokenMapper.mapGoogleTokenToJPAEntity(
            tokenCipher.encrypt(storedCredential.getAccessToken()),
            tokenCipher.encrypt(storedCredential.getRefreshToken())
        )
    );
  }

  public Optional<GoogleTokenResDTO> decryptedTokenLoad(GoogleTokenLoadReqDTO googleTokenLoadReqDTO) {

    String userId = googleTokenLoadReqDTO.getUserId();

    /* 유저 ID가 아닌, 식별자를 의미 */
    Long id = userRepository.findByUserId(userId)
        .orElseThrow(() -> new UserNotExistException(userId)).getId();

    Optional<GoogleTokenJPAEntity> tokenOptional = googleTokenRepository.findById(id);

    return tokenOptional.map(
        token -> tokenMapper.mapGoogleTokenToDTO(
            tokenCipher.decrypt(token.getAccessToken()),
            tokenCipher.decrypt(token.getRefreshToken())
        )
    );
  }
}
