package com.sh.ytb.common.inactive.archive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;
import org.springframework.session.security.web.authentication.SpringSessionRememberMeServices;


/* Spring Session은 동시 세션 제어를 지원하기 위해 Spring Security와 통합 지원
 * e.g.) 단일 사용자가 동시에 가질 수 있는 활성 세션 수를 제한
 * - Spring Security 지원과 달리 클러스터 환경에서도 작동
 * - SessionRegistry인터페이스의 사용자 정의 구현을 제공하여 수행
 */

/* OPTION: XML 기반 구성으로 대체 */
@Configuration
public class SecurityConfig<S extends Session> {

  @Autowired
  private FindByIndexNameSessionRepository<S> sessionRepository;

  @Bean
  public SpringSessionRememberMeServices rememberMeServices() {
    SpringSessionRememberMeServices rememberMeServices =
        new SpringSessionRememberMeServices();
    // optionally customize
    rememberMeServices.setAlwaysRemember(true);
    return rememberMeServices;
  }

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
        /* 동시성 제어 관련 */
        .sessionManagement((sessionManagement) -> sessionManagement
            .maximumSessions(2)
            .sessionRegistry(sessionRegistry())
        )

        /* Spring Security의 Remember-me Authentication와 통합  */
        .rememberMe((rememberMe) -> rememberMe
            .rememberMeServices(rememberMeServices())
        )
        .build();
  }

  @Bean
  public SpringSessionBackedSessionRegistry<S> sessionRegistry() {
    return new SpringSessionBackedSessionRegistry<>(this.sessionRepository);
  }
}