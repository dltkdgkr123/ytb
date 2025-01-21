package com.sh.ytb.dto.res;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class GoogleTokenResDTO {

  private String accessToken;

  private String refreshToken;
}
