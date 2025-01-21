package com.sh.ytb.config;

import com.sh.ytb.specs.TokenCipher;
import com.sh.ytb.specs.impl.AESCipher;
import com.sh.ytb.properties.secret.AESProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class EncryptConfig {

  private final AESProperties aesProperties;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public TokenCipher tokenCipher() {
    return new AESCipher(aesProperties);
  }
}

