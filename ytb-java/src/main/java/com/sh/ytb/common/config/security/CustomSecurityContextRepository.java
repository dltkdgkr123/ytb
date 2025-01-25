package com.sh.ytb.common.config.security;

import com.sh.ytb.common.properties.secret.SessionProperties;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.SecurityContextRepository;


@RequiredArgsConstructor
public class CustomSecurityContextRepository implements SecurityContextRepository {

  private static SessionProperties sessionProperties;

  @Override
  public SecurityContext loadContext(
      HttpRequestResponseHolder requestResponseHolder) { // 세션 아이디를 통해 세션 정보를 조회합니다.
    return SessionAdapter.getSession()
        .map(this::createSecurityContext)
        .orElseGet(this::emptyContext);
  }

  /**
   * Stores the security context on completion of a request.
   *
   * @param context  the non-null context which was obtained from the holder.
   * @param request
   * @param response
   */
  @Override
  public void saveContext(SecurityContext context, HttpServletRequest request,
      HttpServletResponse response) {

  }

  /**
   * Allows the repository to be queried as to whether it contains a security context for the
   * current request.
   *
   * @param request the current request
   * @return true if a context is found for the request, false otherwise
   */
  @Override
  public boolean containsContext(HttpServletRequest request) {
    return request.getCookies(sessionProperties.getSessionId()) != null;
  }


  /* org.springframework.security.core.userdetails.User 관련 (Domain 아님) */
  private SecurityContext createSecurityContext(User user) { // 세션 정보를 통해 SecurityContext를 생성합니다.
    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
        new UsernamePasswordAuthenticationToken(user, user.getAuthorities());
    return new SecurityContextImpl(usernamePasswordAuthenticationToken);
  }

  private SecurityContext emptyContext() { // 세션 정보가 존재하지 않는 경우 빈 SecurityContext를 생성합니다.
    return SecurityContextHolder.createEmptyContext();
  }
}