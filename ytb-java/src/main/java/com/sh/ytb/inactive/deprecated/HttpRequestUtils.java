package com.sh.ytb.inactive.deprecated;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.Optional;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Profile("deprecated")
@Deprecated
@Component
public class HttpRequestUtils {

  public ServletRequestAttributes getRequestAttributes() {

    /*
     * RequestAttributes: interface type (list 아님)
     * getRequestAttributes(): 스레드에 바운드 된 속성 없으면 return null
     */

    // FIXME: 인텔리제이 버그 일단 건의함 보고 수정
    RequestAttributes requestAttributes =
        Optional.ofNullable(RequestContextHolder.getRequestAttributes())
            .orElseThrow(() -> new IllegalStateException("RequestAttribute is null"));

    return (ServletRequestAttributes) requestAttributes;
  }

  public HttpServletRequest getRequest() {

    // FIXME: 인텔리제이 버그 일단 건의함 보고 수정
    return Optional.ofNullable(getRequestAttributes().getRequest())
        .orElseThrow(() -> new IllegalStateException("Request is null"));
  }

  public HttpSession getSession() {

    return Optional.ofNullable(getRequest().getSession())
        .orElseThrow(() -> new IllegalStateException("Session is null"));
  }

  public Optional<Cookie[]> getCookies() {

    return Optional.ofNullable(getRequest().getCookies());
  }

  public Optional<Cookie> getCookie(String name) {

    Optional<Cookie[]> cookies = getCookies();

    if (cookies.isEmpty()) {
      return Optional.empty();
    }

    for (Cookie cookie : cookies.get()) {

      if (name.equals(cookie.getName())) {
        return Optional.of(cookie);
      }
    }

    return Optional.empty();
  }

  public Optional<Cookie> getSessionIdCookie() {

    return getCookie("JSESSIONID");
  }
}
