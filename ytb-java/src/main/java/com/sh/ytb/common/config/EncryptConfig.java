package com.sh.ytb.common.config;

import com.sh.ytb.common.config.impl.AESCipher;
import com.sh.ytb.common.config.spec.TokenCipher;
import com.sh.ytb.common.properties.secret.EncryptProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class EncryptConfig {

  private final EncryptProperties encryptProperties;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public TokenCipher tokenCipher() {
    return new AESCipher(encryptProperties);
  }
}

