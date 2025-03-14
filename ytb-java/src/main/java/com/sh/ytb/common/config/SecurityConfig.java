package com.sh.ytb.common.config;

import com.sh.ytb.common.config.security.SessionFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.session.DisableEncodeUrlFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

  private final CorsConfig corsConfig;
  private final SessionFilter sessionFilter;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .cors(cors -> cors.configurationSource(corsConfig))
        .csrf(AbstractHttpConfigurer::disable)
        .formLogin(AbstractHttpConfigurer::disable)
        .addFilterBefore(sessionFilter, DisableEncodeUrlFilter.class)
        .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());

    return http.build();
  }
}
