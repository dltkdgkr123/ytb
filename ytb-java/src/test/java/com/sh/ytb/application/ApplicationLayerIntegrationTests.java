package com.sh.ytb.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
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
class ApplicationLayerIntegrationTests {

  @LocalServerPort
  private int port;

  @BeforeEach
  void setUp() {
    RestAssured.port = port;
  }

  @Test
  void contextLoad() {
    assertTrue(true);
  }

  @Test
  void indexTest() {

    final ExtractableResponse<Response> response = RestAssured.given().log().all()
        .contentType(MediaType.APPLICATION_JSON_VALUE).get("/home").then().log().all().extract();

    assertEquals(HttpStatus.OK.value(), response.statusCode());
  }
}
