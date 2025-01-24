package com.sh.ytb.mapper;

import com.sh.ytb.entity.GoogleTokenJPAEntity;
import com.sh.ytb.entity.UserJPAEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenMapper {

  public GoogleTokenJPAEntity mapEncryptedGoogleTokenToJPAEntity(UserJPAEntity userJPAEntity,
      String encryptedAccessToken, String encryptedRefreshToken) {

    return
        GoogleTokenJPAEntity.builder()
            //            .id()
            .user(userJPAEntity)
            .accessToken(encryptedAccessToken)
            .refreshToken(encryptedRefreshToken)
            .build();
  }
}
