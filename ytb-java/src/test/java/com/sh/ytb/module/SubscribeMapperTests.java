package com.sh.ytb.module;

import com.sh.ytb.adapter.OAuthHelper;
import com.sh.ytb.adapter.YoutubeHelper;
import com.sh.ytb.mapper.SubscribeMapper;
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

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@TestPropertySource(locations = "file:src/main/resources/application.properties")
public class SubscribeMapperTests {

  @LocalServerPort
  private int port;

  @Autowired
  OAuthHelper oAuthHelper;

  @Autowired
  YoutubeHelper youtubeHelper;

  @Autowired
  SubscribeMapper subscribeMapper;

  @BeforeEach
  void setUp() {
    RestAssured.port = port;
  }

  @Test
  void shouldMapSubscriptionToSubscribeDTOCorrectly() throws IOException, GeneralSecurityException {

    subscribeMapper.mapToDTO(
            youtubeHelper.getSubscribedChannels(
                oAuthHelper.convertToCredential(oAuthHelper.loadStoredCredential()))
        )
        .forEach(System.out::println);
  }
}
