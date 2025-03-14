package com.sh.ytb.module;

import static org.junit.jupiter.api.Assertions.assertFalse;

import com.sh.ytb.adapter.out.google.GoogleOAuthHelper;
import io.restassured.RestAssured;
import java.io.IOException;
import java.security.GeneralSecurityException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations = "file:src/main/resources/application.properties")
class GoogleOAuthHelperTests {

  @Autowired
  GoogleOAuthHelper googleOAuthHelper;

  @Test
  void shouldGenerateValidAuthUri() throws GeneralSecurityException, IOException {

    // Given & When
    String uri = googleOAuthHelper.getAuthorizationUri();

    // Then
    assertFalse(uri.isEmpty());
  }
}
