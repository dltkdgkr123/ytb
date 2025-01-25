package com.sh.ytb.common.config;

import com.sh.ytb.common.config.impl.CustomSessionCookieUtils;
import com.sh.ytb.common.config.spec.SessionCookieUtils;
import com.sh.ytb.common.properties.secret.SessionProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SessionCookieConfig {

  private final SessionProperties sessionProperties;

  @Bean
  public SessionCookieUtils sessionCookieUtils() {

    return new CustomSessionCookieUtils(sessionProperties);
  }
}
