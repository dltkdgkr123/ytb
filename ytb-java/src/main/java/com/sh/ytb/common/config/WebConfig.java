package com.sh.ytb.common.config;

import jakarta.annotation.Nonnull;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
public class WebConfig implements CorsConfigurationSource {

  /* FIXME: 프로덕션 전에 다 열어논거 막기, 필요하다면 프로퍼티화 */
  @Nonnull
  @Override
  public CorsConfiguration getCorsConfiguration(@Nonnull HttpServletRequest request) {

    CorsConfiguration corsConfiguration = new CorsConfiguration();

    corsConfiguration.setAllowedOrigins(List.of("*"));
    corsConfiguration.setAllowedMethods(
        List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
    corsConfiguration.setAllowedHeaders(List.of("*"));
    corsConfiguration.setAllowCredentials(false);
    corsConfiguration.setMaxAge(3600L);

    return corsConfiguration;
  }
}
