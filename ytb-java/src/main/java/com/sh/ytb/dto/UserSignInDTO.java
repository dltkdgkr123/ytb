package com.sh.ytb.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserSignInDTO {

  private String userId;

  private String password;
}
