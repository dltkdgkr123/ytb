package com.sh.ytb.common.config.spec;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;

public interface SessionCookieUtils extends CookieUtils {

  Optional<Cookie> getSessionIdCookie(HttpServletRequest request);

  Cookie makeSessionIdCookie(String sessionId);

//  HttpSession getSession(HttpServletRequest request);

  // 요구사항 기술
}
