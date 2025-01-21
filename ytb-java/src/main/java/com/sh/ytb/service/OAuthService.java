package com.sh.ytb.service;

import com.google.api.client.auth.oauth2.StoredCredential;
import com.sh.ytb.adapter.OAuthHelper;
import com.sh.ytb.specs.TokenCipher;
import com.sh.ytb.dto.GoogleTokenDTO;
import com.sh.ytb.dto.TokenLoadReqDTO;
import com.sh.ytb.entity.GoogleTokenJPAEntity;
import com.sh.ytb.exception.UserNotExistException;
import com.sh.ytb.mapper.TokenMapper;
import com.sh.ytb.repository.GoogleTokenRepository;
import com.sh.ytb.repository.UserRepository;
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

  public boolean tokenStore() throws IOException {

    StoredCredential storedCredential = oAuthHelper.loadStoredCredential();

    googleTokenRepository.save(
        tokenMapper.mapGoogleTokenToJPAEntity(
            tokenCipher.encrypt(storedCredential.getAccessToken()),
            tokenCipher.encrypt(storedCredential.getRefreshToken())
        )
    );

    return true;
  }

  public Optional<GoogleTokenDTO> tokenLoad(TokenLoadReqDTO tokenLoadReqDTO) {

    String userId = tokenLoadReqDTO.getUserId();

    /* 유저 ID가 아닌, 식별자를 의미 */
    Long id = userRepository.findByUserId(userId)
        .orElseThrow(() -> new UserNotExistException(userId)).getId();

    Optional<GoogleTokenJPAEntity> tokenOptinal = googleTokenRepository.findById(id);

    if (tokenOptinal.isPresent()) {
      return Optional.of(tokenMapper.mapJPAEntityToDTO(tokenOptinal.get()));
    }
    return Optional.empty();
  }
}
