package com.sh.ytb.properties.secret;

import lombok.Getter;
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

  String apiKey;
}