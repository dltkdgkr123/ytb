package com.sh.ytb.mapper;

import com.sh.ytb.dto.GoogleTokenDTO;
import com.sh.ytb.entity.GoogleTokenJPAEntity;
import com.sh.ytb.specs.impl.AESCipher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenMapper {

  private AESCipher aesCipher;

  public GoogleTokenJPAEntity mapGoogleTokenToJPAEntity(String accessToken, String refreshToken) {

    return
        GoogleTokenJPAEntity.builder()
            .accessToken(aesCipher.encrypt(accessToken))
            .refreshToken(aesCipher.encrypt(refreshToken))
            .build();
  }

  public GoogleTokenDTO mapJPAEntityToDTO(GoogleTokenJPAEntity googleTokenJPAEntity) {

    /* encoding 된 상태임 */
    return
        GoogleTokenDTO.builder()
            .accessToken(googleTokenJPAEntity.getAccessToken())
            .refreshToken(googleTokenJPAEntity.getRefreshToken())
            .build();
  }
}
