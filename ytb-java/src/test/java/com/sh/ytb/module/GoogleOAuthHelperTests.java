package com.sh.ytb.module;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.sh.ytb.adapter.out.google.GoogleOAuthHelper;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.io.IOException;
import java.security.GeneralSecurityException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;


/* FIXME: OAuth API 테스트와 mock 기반 테스트로 분리 */
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@TestPropertySource(locations = "file:src/main/resources/application.properties")
class GoogleOAuthHelperTests {

  @LocalServerPort
  private int port;

  @Autowired
  GoogleOAuthHelper googleOAuthHelper;

  @BeforeEach
  void setUp() {
    RestAssured.port = port;
  }

  /* FIXME: "URI가 비어있지 않다" 보다 신빙성있는 검증 필요 */
  @Test
  void shouldGenerateValidAuthUri() {

    final ExtractableResponse<Response> response = RestAssured.given().log().all()
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .when().get("/oauth/google-auth").then().log().all().extract();

    String uri = response.body().asString();

    boolean success = !uri.isEmpty();

    assertEquals(HttpStatus.OK.value(), response.statusCode());
    assertTrue(success);
  }

  /*
  * TODO: 검증 필요
  *  - 인증 플로우에 data store을 셋팅하지 않으면 파일을 저장하지 않는다.
  *  - 핸들러로 반환된 토큰은 유효하다.
  */
  @Test
  @Sql("/UserTest.sql")
  void test() throws GeneralSecurityException, IOException, InterruptedException {

    String uri = googleOAuthHelper.getAuthorizationUri();
    System.out.println("=========================");
    System.out.println(uri);
    System.out.println("=========================");

    Thread.sleep(36000);
  }

}
