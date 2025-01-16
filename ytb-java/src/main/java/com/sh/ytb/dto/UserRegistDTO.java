package com.sh.ytb.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserRegistDTO {

  private String name;

  private String password;
}
