package com.sh.ytb.app.mapper;

import com.sh.ytb.adapter.out.jpa.entity.GoogleTokenJPAEntity;
import com.sh.ytb.adapter.out.jpa.entity.UserJPAEntity;
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
