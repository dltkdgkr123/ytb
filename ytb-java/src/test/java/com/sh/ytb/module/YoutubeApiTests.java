package com.sh.ytb.module;

import com.sh.ytb.adapter.OAuthHelper;
import com.sh.ytb.adapter.YoutubeHelper;
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
public class YoutubeApiTests {

  @LocalServerPort
  private int port;

  @BeforeEach
  void setUp() {
    RestAssured.port = port;
  }

  @Autowired
  YoutubeHelper youtubeHelper;

  @Autowired
  OAuthHelper oAuthHelper;

  /* FIXME : 프젝 볼륨 커지면 삭제 */
  @Test
  void shouldReturnTop10PopularMusicVideos_WhenApiKeyValid() {

    final ExtractableResponse<Response> response = RestAssured.given().log().all()
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .when().get("/youtube/video/mostPopular").then().log().all().extract();

    Assertions.assertEquals(HttpStatus.OK.value(), response.statusCode());
  }

  /* TODO: 구현 및 필요한 필드만 사용 - reources/doc/subscribeJsonFields.txt */
  @Test
  void shouldReturnSubscribedChannels_WhenCredentialValid() {

  }
}
