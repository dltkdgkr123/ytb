package com.sh.ytb.mapper;

import com.sh.ytb.entity.GoogleTokenJPAEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenMapper {

  private PasswordEncoder tokenEncoder;

  public GoogleTokenJPAEntity mapToGoogleTokenJPAEntity(String accessToken, String refreshToken) {

    return
        GoogleTokenJPAEntity.builder()
            .accessToken(tokenEncoder.encode(accessToken))
            .refreshToken(tokenEncoder.encode(refreshToken))
            .build();
  }
}
