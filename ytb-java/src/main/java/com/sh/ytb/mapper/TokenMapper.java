package com.sh.ytb.mapper;

import com.sh.ytb.dto.GoogleTokenDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenMapper {

  private PasswordEncoder tokenEncoder;

  public GoogleTokenDTO mapObjToDTO(GoogleTokenDTO googleTokenDTO) {

    return
        GoogleTokenDTO.builder()
            .accessToken(tokenEncoder.encode(googleTokenDTO.getAccessToken()))
            .refreshToken(tokenEncoder.encode(googleTokenDTO.getRefreshToken()))
            .build();
  }
}
