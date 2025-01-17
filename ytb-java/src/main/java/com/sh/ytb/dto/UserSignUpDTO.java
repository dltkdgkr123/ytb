package com.sh.ytb.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserSignUpDTO {

  private String userId;

  private String password;
}
