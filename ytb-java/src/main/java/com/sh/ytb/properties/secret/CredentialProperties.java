package com.sh.ytb.properties.secret;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(
    ignoreInvalidFields = false,
    ignoreUnknownFields = false,
    prefix = "credential")
@Component
@Getter
@Setter
public class CredentialProperties {

  private CredentialProperties() {
  }

  String apiKey;
}