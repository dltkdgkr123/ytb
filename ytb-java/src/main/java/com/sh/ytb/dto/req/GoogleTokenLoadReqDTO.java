package com.sh.ytb.dto.req;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class GoogleTokenLoadReqDTO {

  private String userId;
}
