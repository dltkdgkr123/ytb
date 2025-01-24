package com.sh.ytb.module;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.sh.ytb.adapter.out.google.GoogleOAuthHelper;
import com.sh.ytb.adapter.out.google.YoutubeHelper;
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

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@TestPropertySource(locations = "file:src/main/resources/application.properties")
class YoutubeApiTests {

  @LocalServerPort
  private int port;

  @BeforeEach
  void setUp() {
    RestAssured.port = port;
  }

  @Autowired
  YoutubeHelper youtubeHelper;

  @Autowired
  GoogleOAuthHelper googleOAuthHelper;

  /* FIXME : 프젝 볼륨 커지면 삭제 */
  @Test
  void shouldReturnTop10PopularMusicVideos_WhenApiKeyValid() {

    final ExtractableResponse<Response> response = RestAssured.given().log().all()
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .when().get("/youtube/video/mostPopular").then().log().all().extract();

    assertEquals(HttpStatus.OK.value(), response.statusCode());
  }

  /* TODO: 구현 및 필요한 필드만 사용 - reources/doc/SubscribeJsonFields.txt */
  @Test
  void shouldReturnSubscribedChannels_WhenCredentialValid() {

  }
}
