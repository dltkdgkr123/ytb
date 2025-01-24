package com.sh.ytb.app.dto.res;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class UserSignInResDTO {

  private boolean hasToken;
}
