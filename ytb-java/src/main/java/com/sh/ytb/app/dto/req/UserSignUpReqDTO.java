package com.sh.ytb.app.dto.req;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserSignUpReqDTO {

  private String userId;

  private String password;
}
