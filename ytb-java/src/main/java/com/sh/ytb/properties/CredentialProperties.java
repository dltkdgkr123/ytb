package com.sh.ytb.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(
    ignoreInvalidFields = false,
    ignoreUnknownFields = false,
    prefix = "secret")
@Component
@Getter
@Setter
public class CredentialProperties {

  String apiKey;
}