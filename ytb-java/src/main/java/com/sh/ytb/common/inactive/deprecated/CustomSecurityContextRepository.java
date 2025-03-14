package com.sh.ytb.common.inactive.deprecated;

import com.sh.ytb.common.config.spec.SessionCookieUtils;
import com.sh.ytb.common.properties.secret.SessionProperties;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.DeferredSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.SecurityContextRepository;


/* SecurityContext 안쓰고 가볍게 보안 구성이 목표여서 폐기 */
@Deprecated(forRemoval = true)
@RequiredArgsConstructor
public class CustomSecurityContextRepository implements SecurityContextRepository {

  private static SessionProperties sessionProperties;
  private static SessionCookieUtils sessionCookieUtils;


  /* 폐기 예정이라고 loadDeferredContext 쓰라는데 정작 이거 구현 안하면 상속 오류 뜸 */
  @Override
  public SecurityContext loadContext(HttpRequestResponseHolder requestResponseHolder) {
    return null;
  }

  @Override
  public DeferredSecurityContext loadDeferredContext(HttpServletRequest request) {
    return SecurityContextRepository.super.loadDeferredContext(request);
  }


  @Override
  public void saveContext(SecurityContext context, HttpServletRequest request,
      HttpServletResponse response) {

  }


  @Override
  public boolean containsContext(HttpServletRequest request) {
    return true;
  }


  /* org.springframework.security.core.userdetails.User 관련 (Domain 아님) */
  private SecurityContext createSecurityContext(User user) {

    return
        new SecurityContextImpl(
            new UsernamePasswordAuthenticationToken(user, user.getAuthorities()));
  }

  private SecurityContext emptyContext() { // 세션 정보가 존재하지 않는 경우 빈 SecurityContext를 생성합니다.
    return SecurityContextHolder.createEmptyContext();
  }
}