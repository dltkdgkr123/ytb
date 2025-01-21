package com.sh.ytb.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserSignUpReqDTO {

  private String userId;

  private String password;
}
