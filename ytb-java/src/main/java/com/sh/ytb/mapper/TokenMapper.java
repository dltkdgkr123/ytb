package com.sh.ytb.mapper;

import com.sh.ytb.dto.res.GoogleTokenResDTO;
import com.sh.ytb.entity.GoogleTokenJPAEntity;
import com.sh.ytb.properties.OAuthProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenMapper {

  public GoogleTokenJPAEntity mapGoogleTokenToJPAEntity(String accessToken, String refreshToken) {

    return
        GoogleTokenJPAEntity.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .build();
  }

  public GoogleTokenResDTO mapGoogleTokenToDTO(String accessToken, String refreshToken) {

    return
        GoogleTokenResDTO.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .build();
  }
}
