package com.sh.ytb.common.config;

import com.sh.ytb.common.config.impl.SessionGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SessionGeneratorConfig {

  @Bean
  public SessionGenerator sessionGenerator() {

    return new SessionGenerator();
  }
}
