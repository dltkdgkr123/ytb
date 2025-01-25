package com.sh.ytb.common.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.session.data.redis.RedisSessionRepository;

/* OPTION:
 * - RedisIndexedSessionRepository, ReactiveRedisSessionRepository, ReactiveRedisIndexedSessionRepository
 */
@Configuration
@RequiredArgsConstructor
public class SessionRepositoryConfig {

  private final RedisTemplate<String, Object> redisTemplate;

  @Bean
  public RedisSessionRepository redisSessionRepository() {

    /* 자세한 설정은 .properties에 위치 */
    return new RedisSessionRepository(redisTemplate);
  }
}
