package com.sh.ytb.properties.secret;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@ConfigurationProperties(
    ignoreInvalidFields = false,
    ignoreUnknownFields = false,
    prefix = "encrypt")
@Component
@Setter
public class EncryptProperties {

  private EncryptProperties() {
  }

  String aesSecretKey;

  public SecretKey getAESSecretKey() {
    return new SecretKeySpec(aesSecretKey.getBytes(), "AES");
  }
}
