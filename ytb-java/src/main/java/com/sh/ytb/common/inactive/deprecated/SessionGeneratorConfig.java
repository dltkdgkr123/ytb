package com.sh.ytb.common.inactive.deprecated;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/* 세션 톰캣이 자동 생성 해서 폐기 */
@Deprecated(forRemoval = true)
@Configuration
@RequiredArgsConstructor
public class SessionGeneratorConfig {

  @Bean
  public SessionGenerator sessionGenerator() {

    return new CustomSessionGenerator();
  }
}
