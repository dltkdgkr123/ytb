package com.sh.ytb.common.config.impl;

import com.sh.ytb.common.config.spec.CookieUtils;
import com.sh.ytb.common.properties.secret.SessionProperties;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@RequiredArgsConstructor
public class SessionCookieUtils implements CookieUtils {

  private final SessionProperties sessionProperties;

  public void addCookie(HttpServletResponse response, Cookie cookie) {

    /* .currentRequestAttributes(): 스레드에 바운드 된 속성 없으면 throw IllegalStateException(null 처리 불필요)*/
    Optional.of(
            ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse())
        .orElseThrow(() -> new IllegalStateException("Response is null"))
        .addCookie(cookie);
  }

  public Cookie makeCookie(String name, String value, int expiryTimeSecs) {

    Cookie cookie = new Cookie(name, value);

    /*
     * Option:
     *
     * Lax: 동일한 도메인 내 요청 및 일부 GET 요청에서만 전송.
     * None: 모든 요청에서 쿠키를 전송(이 경우 반드시 Secure 플래그 필요).
     */
    cookie.setAttribute("SameSite", "Strict"); // 동일한 도메인 내에서만 쿠키를 전송 - CSRF 방지
    cookie.setHttpOnly(true); // JavaScript에서 쿠키에 접근 불가 - XSS 방지
    cookie.setSecure(true); // HTTPS를 통해서만 전송 - 탈취 방지
    cookie.setPath("/"); // 도메인의 모든 경로에서 쿠키가 전송
    cookie.setMaxAge(expiryTimeSecs);

    return cookie;
  }

  public Cookie makeSessionIdCookie(String sessionId) {

    return makeCookie(sessionProperties.getSessionId(), sessionId, 1800);
  }
}
