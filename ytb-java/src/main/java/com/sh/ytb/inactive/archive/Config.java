package com.sh.ytb.inactive.archive;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/* HttpSession을 Spring Session에서 지원하는 구현으로 대체하는 Servlet Filter 생성 */
/* spring.session.store-type 환경변수 지정 시 AutoConfiguration으로 인해 같은 효과 */
@Configuration(proxyBeanMethods = false)
@EnableRedisHttpSession
public class Config {

  @Bean
  public LettuceConnectionFactory connectionFactory() {

    return new LettuceConnectionFactory();
  }

}