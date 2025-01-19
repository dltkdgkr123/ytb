package com.sh.ytb.inactive.deprecated;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("deprecated")
@Deprecated
@ConfigurationProperties(
    ignoreInvalidFields = false,
    ignoreUnknownFields = false,
    prefix = "aes")
@Component
@Getter
@Setter
public class AESProperties {

  String secretKey; // 사용시, 해당 환경 변수 정의 필요
}