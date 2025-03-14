package com.sh.ytb.common.config.security;

import com.sh.ytb.common.config.spec.SessionCookieUtils;
import com.sh.ytb.common.exception.SessionInvalidException;
import com.sh.ytb.common.exception.SessionNotExistException;
import com.sh.ytb.common.properties.secret.SessionProperties;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class SessionFilter extends OncePerRequestFilter {

  private final StringRedisTemplate stringRedisTemplate;
  private final SessionProperties sessionProperties;

  private final SessionCookieUtils sessionCookieUtils;
  /* OPTION: private final RedisTemplate<String, Object> redisTemplate; 직접 사용*/
  private final HttpSession session; // Spring Session 사용 중 - 구현체가 Redis로 변경

  @Override
  public void doFilterInternal(
      @Nonnull HttpServletRequest request,
      @Nonnull HttpServletResponse response,
      @Nonnull FilterChain filterChain) throws ServletException, IOException {

    try {
      /* TODO: 구현을 위해 Session 관계 없이 모든 요청에 열린 상태 수정 */
//      Cookie sessionIdCookie = sessionCookieUtils.getSessionIdCookie(request)
//          .orElseThrow(SessionNotExistException::new);

      filterChain.doFilter(request, response);
    }

    /*
     * Filter 단의 예외이기 때문에 전역 핸들러를 거치지 않음
     * 따라서 의도한 403이 아닌 500으로 반환되기 때문에 별도로 처리하여 403 반환
     */ catch (SessionNotExistException | SessionInvalidException e) {
      response.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
    }
  }

  @Override
  protected boolean shouldNotFilter(@Nonnull HttpServletRequest request) {

    final List<String> excludeUrlPatterns = List.of("/home", "/user/sign-in");

    return excludeUrlPatterns.stream()
        .anyMatch(p -> Objects.equals(p, request.getServletPath()));
  }
}