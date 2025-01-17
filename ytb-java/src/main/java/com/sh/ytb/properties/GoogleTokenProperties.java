package com.sh.ytb.properties;

import org.springframework.stereotype.Component;

@Component
public class GoogleTokenProperties {

  public static final Long expiration_time_millis = 1800000L; // 30 Minutes
}
