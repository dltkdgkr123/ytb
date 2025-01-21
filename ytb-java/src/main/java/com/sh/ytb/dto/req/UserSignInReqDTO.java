package com.sh.ytb.dto.req;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserSignInReqDTO {

  private String userId;

  private String password;
}
