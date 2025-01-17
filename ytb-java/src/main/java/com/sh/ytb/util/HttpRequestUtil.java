package com.sh.ytb.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.Optional;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class HttpRequestUtil {


  public ServletRequestAttributes getAttributes() {

    return Optional.of((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
        .orElseThrow(IllegalStateException::new);
  }

  public HttpServletRequest getRequest() {

    return Optional.of(getAttributes().getRequest())
        .orElseThrow(IllegalStateException::new);
  }


  public HttpSession getSession() {

    return Optional.of(getRequest().getSession())
        .orElseThrow(IllegalStateException::new);
  }

  public Optional<Cookie[]> getCookies() {

    return Optional.ofNullable(getRequest().getCookies());
  }

  public Optional<Cookie> getCookie(String cookieName) {

    // TODO: 구현

    return null;
  }
}
