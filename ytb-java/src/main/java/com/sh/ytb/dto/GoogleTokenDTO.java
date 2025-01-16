package com.sh.ytb.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class GoogleTokenDTO {

  private String accessToken;

  private String refreshToken;
}
