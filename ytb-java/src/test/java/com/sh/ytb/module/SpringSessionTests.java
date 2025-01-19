package com.sh.ytb.module;

import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class SpringSessionTests {

  @Autowired
  HttpSession session;

  @Test
  void testSession() {

  }
}
