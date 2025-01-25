package com.sh.ytb.common.properties.secret;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(
    ignoreInvalidFields = false,
    ignoreUnknownFields = false,
    prefix = "session")
@Component
@Getter
@Setter
public class SessionProperties {

  private SessionProperties() {
  }

  /* Spring Session과 Filter에 사용될 Session의 동기화를 위함 */
  @Value("${server.servlet.session.cookie.name}")
  String sessionId;
}