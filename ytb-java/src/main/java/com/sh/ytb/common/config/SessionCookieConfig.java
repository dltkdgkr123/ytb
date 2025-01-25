package com.sh.ytb.common.config;

import com.sh.ytb.common.config.impl.SessionCookieUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SessionCookieConfig {

  @Bean
  public SessionCookieUtils sessionCookieUtils() {

    return new SessionCookieUtils();
  }
}
