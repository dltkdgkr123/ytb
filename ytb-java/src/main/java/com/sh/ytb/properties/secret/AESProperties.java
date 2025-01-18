package com.sh.ytb.properties.secret;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/*
* FIXME:
*   - Spring Session이 지혼자 다해서 필요가 없는 것같음
*   - 만약 지우게된다면, 환경변수도 같이 삭제
*/
@Deprecated
@ConfigurationProperties(
    ignoreInvalidFields = false,
    ignoreUnknownFields = false,
    prefix = "aes")
@Component
@Getter
@Setter
public class AESProperties {

  String secretKey;
}