package com.sh.ytb.properties;

import org.springframework.stereotype.Component;

@Component
public class SubscribeProperties {

  public static final String subscribe_application_name = "subscribes";
  public static final Long subscribe_max_results = 100L;
  public static final String subscribe_scope = "snippet";
  public static final boolean subscribe_is_mine = true; // OAuth 인증 사용자가 구독한 채널만 반환

  /* TODO: 정렬 기준 확인 */
//  public static final String subscribe_order = ""
}
