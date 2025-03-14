package com.sh.ytb.common.inactive.deprecated;

import org.apache.catalina.util.StandardSessionIdGenerator;

/* 세션 톰캣이 자동 생성 해서 폐기 */
@Deprecated(forRemoval = true)
/*
 * StandardSessionIdGenerator implements org.apache.catalina.SessionIdGenerator
 * ref: https://tomcat.apache.org/tomcat-9.0-doc/config/sessionidgenerator.html
 */
public class CustomSessionGenerator extends StandardSessionIdGenerator implements SessionGenerator {

  @Override
  public String generateSessionId() {
    return super.generateSessionId();
  }
}
