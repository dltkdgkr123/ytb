package com.sh.ytb.api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.sh.ytb.app.dto.req.UserSignInReqDTO;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@TestPropertySource(locations = "file:src/main/resources/application.properties")
@EnableRedisHttpSession(redisNamespace = "${spring.session.redis.namespace}")
class SessionFilterRequestTests {

  @LocalServerPort
  private int port;

  @BeforeEach
  void setUp() {
    RestAssured.port = port;
  }

  /* OPTION: Mocking */
  /* FIXME: Redis 세션 조회 */
  @Test
  @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
  @Sql("/UserTest.sql")
  void shouldGenerateNewSession_WhenUserSignInSuccess() {

    // Given
    String singInUrl = "/user/sign-in";
    int expectedHttpStatus = 200; // HttpStatus.OK.value()

    UserSignInReqDTO userSignInReqDTO = UserSignInReqDTO.builder()
        .userId("id3001")
        .password("password3001")
        .build();

    // When
    final ExtractableResponse<Response> response = RestAssured.given().log().all()
        .contentType(MediaType.APPLICATION_JSON_VALUE).body(userSignInReqDTO)
        .post(singInUrl).then().log().all().extract();

    // Then
    assertEquals(expectedHttpStatus, response.statusCode());
  }

  /* OPTION: Mocking */
  @Test
  void shouldAllowAccess_ForExcludedUrl() {

    // Given
    String excludedUrl = "/home";
    int expectedHttpStatus = 200; // HttpStatus.OK.value()
    String expectedValue = "Hello";

    // When
    final ExtractableResponse<Response> response = RestAssured.given().log().all()
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .get(excludedUrl).then().log().all().extract();

    // Then
    assertEquals(expectedHttpStatus, response.statusCode());
    assertEquals(expectedValue, response.body().asString());
  }

  /* OPTION: Mocking */
  @Test
  void shouldDenyAccess_forNonExcludedUrl_whenSessionNotExist() {

    // Given
    String NonExcludedUrl = "/oauth/google-auth";
    int expectedHttpStatus = 403; // HttpStatus.FORBIDDEN.value()

    // When
    final ExtractableResponse<Response> response = RestAssured.given().log().all()
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .post(NonExcludedUrl).then().log().all().extract();

    // Then
    assertEquals(expectedHttpStatus, response.statusCode());
  }
}
