package com.sh.ytb.module;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;

import com.sh.ytb.adapter.in.web.UserController;
import com.sh.ytb.app.dto.req.UserSignInReqDTO;
import com.sh.ytb.app.service.UserService;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
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

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@TestPropertySource(locations = "file:src/main/resources/application.properties")
@Sql("/UserTest.sql")
public class SpringSessionTests {

  @LocalServerPort
  private int port;

  @BeforeEach
  void setUp() {
    RestAssured.port = port;
  }

  @Autowired
  UserController userController;

  @Autowired
  UserService userService;

  @Test
  void testSession() {

    UserSignInReqDTO userSignInReqDTO = UserSignInReqDTO.builder()
        .userId("id3001")
        .password("password3001")
        .build();

    final ExtractableResponse<Response> response = RestAssured.given().log().all()
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .body(userSignInReqDTO).post("/user/sign-in").then().log().all().extract();

    assertEquals(HttpStatus.OK.value(), response.statusCode());
  }

  @Test
  void setCustomSession() throws InterruptedException {

    final ExtractableResponse<Response> response = RestAssured.given().log().all()
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .post("/home").then().log().all().extract();

    assertEquals(HttpStatus.OK.value(), response.statusCode());

    Thread.sleep(1000000);
  }
}
