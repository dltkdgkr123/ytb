package com.sh.ytb.common.config.spec;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

public interface CookieUtils {

  Cookie makeCookie(String name, String value, int expiryTimeSecs);

  void addCookie(HttpServletResponse response, Cookie cookie);

  // 요구사항 기술
}
