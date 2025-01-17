package com.sh.ytb.util;

import jakarta.servlet.http.HttpSession;
import java.util.Optional;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class SessionUtil {

  public HttpSession getSession() {

    return
        Optional.of((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
            .orElseThrow(IllegalStateException::new)
            .getRequest()
            .getSession();
  }

}
