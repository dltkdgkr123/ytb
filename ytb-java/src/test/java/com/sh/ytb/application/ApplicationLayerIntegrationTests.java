package com.sh.ytb.application;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@TestPropertySource(locations = "file:src/main/resources/application.properties")
public class ApplicationLayerIntegrationTests {

  @LocalServerPort
  private int port;

  @BeforeEach
  void setUp() {
    RestAssured.port = port;
  }

  @Test
  void shouldReturnCorrectHttpStatusAndResponse() {
    String expectedMsg = "hello client";

    final ExtractableResponse<Response> response = RestAssured.given().log().all()
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .when().get("/youtube").then().log().all().extract();

    Assertions.assertEquals(HttpStatus.OK.value(), response.statusCode());
    Assertions.assertEquals(expectedMsg, response.body().asString());
  }

  @Test
  void shouldReturnTop10PopularMusicVideos_WhenApiKeyIsValid() {

    final ExtractableResponse<Response> response = RestAssured.given().log().all()
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .when().get("/youtube/video/mostPopular").then().log().all().extract();

    Assertions.assertEquals(HttpStatus.OK.value(), response.statusCode());

    // 직렬화하고 던져서 테스트에서 아직 컨텐츠는 컨디션 파악하기 애매함
  }
}
