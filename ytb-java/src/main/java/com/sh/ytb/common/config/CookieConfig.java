package com.sh.ytb.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

@Configuration
public class CookieConfig {

  @Bean
  public CookieSerializer cookieSerializer() {
    return new DefaultCookieSerializer();
  }

}

