package com.sh.ytb.common.config;

import com.sh.ytb.common.config.impl.CustomSerializer;
import com.sh.ytb.common.config.spec.SessionSerializer;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SessionSerializerConfig {

  @Bean
  public SessionSerializer sessionSerializer() {

    return new CustomSerializer();
  }
}

