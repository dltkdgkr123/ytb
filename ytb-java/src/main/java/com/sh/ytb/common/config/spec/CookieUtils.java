package com.sh.ytb.common.config.spec;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Optional;

public interface CookieUtils {

  Cookie makeCookie(String name, String value, int expiryTimeSecs);

  void addCookie(HttpServletResponse response, Cookie cookie);

  Optional<Cookie> getCookie(HttpServletRequest request, String name);

  // 요구사항 기술
}
