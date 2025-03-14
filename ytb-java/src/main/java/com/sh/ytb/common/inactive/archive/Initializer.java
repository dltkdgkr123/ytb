package com.sh.ytb.common.inactive.archive;

import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;

/* 모든 요청에 대해 springSessionRepositoryFilter가 Servlet Container에 등록됨 */
public class Initializer extends AbstractHttpSessionApplicationInitializer {

  public Initializer() {
    super(Config.class);
  }

}