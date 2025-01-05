package com.sh.ytb;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
class ApplicationTests {


  /**
   *  (mocking을 하지 않은 실제) Controller, Service Layer의 REST 통신 검증
   */
  @Test
  void shouldReturnCorrectHttpStatusAndResponse() {
    String expectedMsg = "hello client";

    final ExtractableResponse<Response> response = RestAssured.given().log().all()
        .contentType(MediaType.APPLICATION_JSON_VALUE)
//            .body()
        .when().get("/youtube").then().log().all().extract();

    Assertions.assertEquals(response.statusCode(), HttpStatus.OK.value());
    Assertions.assertEquals(response.body().asString(), expectedMsg);
  }
}
