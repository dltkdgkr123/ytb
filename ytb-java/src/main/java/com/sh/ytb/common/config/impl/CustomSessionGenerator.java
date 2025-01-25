package com.sh.ytb.common.config.impl;

import com.sh.ytb.common.config.spec.SessionGenerator;
import org.apache.catalina.util.StandardSessionIdGenerator;

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
