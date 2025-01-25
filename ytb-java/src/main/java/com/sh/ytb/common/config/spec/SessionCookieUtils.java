package com.sh.ytb.common.config.spec;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public interface SessionCookieUtils extends CookieUtils {

  @Override
  Cookie makeCookie(String name, String value, int expiryTimeSecs);

  @Override
  void addCookie(HttpServletResponse response, Cookie cookie);

  Cookie makeSessionIdCookie(String sessionId);

  HttpSession getSession(HttpServletRequest request);

  // 요구사항 기술
}
