package com.sh.ytb.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.sh.ytb.app.dto.req.UserSignInReqDTO;
import com.sh.ytb.common.config.spec.SessionSerializer;
import com.sh.ytb.common.properties.secret.SessionProperties;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "file:src/main/resources/application.properties")
@EnableRedisHttpSession(redisNamespace = "${spring.session.redis.namespace}")
@Sql("/UserTest.sql")
class UserApiTests {

  MockHttpServletRequest request_;

  @LocalServerPort
  private int port;

  @Autowired
  SessionSerializer sessionSerializer;

  @Autowired
  StringRedisTemplate stringRedisTemplate;


  @Autowired
  SessionProperties sessionProperties;

  @BeforeEach
  void setUp() {
    RestAssured.port = port;
    request_ = new MockHttpServletRequest();
  }


  /**
   * Spring Session Scope
   * 1. UUID Session 생성
   * 2. Redis -> UUID Session 저장
   * 3. Response -> BASE64 Session "SESSION" Cookie 에 저장
   * <p>
   * Test Scope
   * 1. Response -> "SESSION" Cookie 에서 BASE64 Session 조회
   * 2. BASE64 Session -> UUID Session 디코딩
   * 3. ID -> Redis Path + BASE64 Session
   * 4. Redis -> ID로 Session Hash 조회
   * 5. storedUserId -> Session Hash 의 "sessionAttr:userId" 속성 조회
   * 6. UserId -> storedUserId Byte Stream 에서 UserId 추출
   * <p>
   * Assert Scope
   * 6. HttpStatus 200 검증
   * 7. UserId 정상 조회 검증
   **/
  @Test
  void signIn_shouldSucceed_whenUserInfoMatched() {

    // Given
    String url = "/user/sign-in";
    int expectedHttpStatus = 200; // HttpStatus.OK.value()

    UserSignInReqDTO userSignInReqDTO = UserSignInReqDTO.builder()
        .userId("id3001")
        .password("password3001")
        .build();

    String sessionRedisPath = sessionProperties.getSessionPath();

    // When
    final ExtractableResponse<Response> response = RestAssured.given().log().all()
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .body(userSignInReqDTO)
        .post(url).then().log().all().extract();

    // Then
    String sessionId = response.cookie("SESSION");
    String redisSessionId = sessionRedisPath + sessionSerializer.Base64StringToString(sessionId);

    Map<Object, Object> storedSessionHash = stringRedisTemplate.opsForHash()
        .entries(redisSessionId);
    String storedUserId = sessionSerializer.ByteStreamToString(
        storedSessionHash.get("sessionAttr:userId")
            .toString()
            .getBytes()
    );

    assertEquals(expectedHttpStatus, response.statusCode());
    assertEquals("id3001", storedUserId);

    System.out.println(storedUserId);
  }
}
