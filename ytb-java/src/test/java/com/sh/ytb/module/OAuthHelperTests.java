package com.sh.ytb.module;

import com.sh.ytb.OAuthHelper;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@TestPropertySource(locations = "file:src/main/resources/application.properties")
public class OAuthHelperTests {

  @LocalServerPort
  private int port;

  @Autowired
  OAuthHelper oAuthHelper;

  @BeforeEach
  void setUp() {
    RestAssured.port = port;
  }

  /* TODO: "URI가 비어있지 않다" 보다 신빙성있는 검증 필요 */
  @Test
  void OAuthHelper_shouldGenerateValidAuthUri() {

    final ExtractableResponse<Response> response = RestAssured.given().log().all()
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .when().get("/youtube/auth").then().log().all().extract();

    String uri = response.body().asString();

    boolean success = !uri.isEmpty();

    Assertions.assertEquals(HttpStatus.OK.value(), response.statusCode());
    Assertions.assertTrue(success);
  }

  /** TODO : 구현 필요 */
  @Test
  void OAuthHelper_shouldStoreValidCredentialInCredentialsDir_WhenAuthCodeValid() {

  }

  /** TODO : 구현 필요 */
  @Test
  void OAuthHelper_shouldGenerateValidCredentialObj_WhenStoredCredentialVaild() throws Exception {

    oAuthHelper.loadCredentialObjFromStoredCredential();
  }
}
