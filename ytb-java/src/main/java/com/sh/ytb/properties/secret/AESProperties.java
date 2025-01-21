package com.sh.ytb.properties.secret;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(
    ignoreInvalidFields = false,
    ignoreUnknownFields = false,
    prefix = "aes")
@Component
@Getter
@Setter
public class AESProperties {

  String secretKey;

  public SecretKey getSecretKeyForAES() {
    return new SecretKeySpec(secretKey.getBytes(), "AES");
  }
}
