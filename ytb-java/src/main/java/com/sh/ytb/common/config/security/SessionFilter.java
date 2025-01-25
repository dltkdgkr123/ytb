package com.sh.ytb.common.config.security;

import com.sh.ytb.common.config.spec.SessionCookieUtils;
import com.sh.ytb.common.config.spec.SessionGenerator;
import com.sh.ytb.common.properties.secret.SessionProperties;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.web.filter.OncePerRequestFilter;


@RequiredArgsConstructor
public class SessionFilter extends OncePerRequestFilter {

  private final ThreadLocal<String> sessionIdHolder = new ThreadLocal<>();
  private final SessionProperties sessionProperties;
  private final SessionGenerator sessionGenerator;
  private final SessionCookieUtils sessionCookieUtils;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      @Nonnull FilterChain filterChain) throws ServletException, IOException {

    /*
     * request에서 cookie 존재 유무 판별
     * - cookie가 있다면 그대로 사용
     * - cookie가 없다면 만들고 response에 추가 후 사용
     * */
    String sessionId =
        Arrays.stream(request.getCookies())
            /* Option: null-safe 비교 중, 어차피 로직 상 null이면 안됨 */
            .filter(cookie -> Objects.equals(cookie.getName(), sessionProperties.getSessionId()))
            .findFirst()
            .orElseGet(() -> {

              String newSessionId = sessionGenerator.generateSessionId();
              Cookie newSessionIdCookie = sessionCookieUtils.makeSessionIdCookie(
                  newSessionId);
              sessionCookieUtils.addCookie(response, newSessionIdCookie);

              return newSessionIdCookie;
            })
            .getValue();

    sessionIdHolder.set(sessionId);
    filterChain.doFilter(request, response); // 다음 필터로 sessionId or newSessionId를 넘긺
    sessionIdHolder.remove();
  }
}