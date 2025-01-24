package com.sh.ytb.module;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.sh.ytb.app.dto.req.UserSignInReqDTO;
import com.sh.ytb.app.dto.res.UserSignInResDTO;
import com.sh.ytb.common.exception.PasswordNotMatchException;
import com.sh.ytb.common.exception.UserNotExistException;
import com.sh.ytb.app.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "file:src/main/resources/application.properties")
@Sql("/UserTest.sql")
class UserTests {

  @Autowired
  private UserService userService;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Test
  void signIn_shouldSucceed_whenUserInfoMatched() {

    // Given
    UserSignInReqDTO userSignInReqDTO = UserSignInReqDTO.builder()
        .userId("id3001")
        .password("password3001")
        .build();

    // When
    UserSignInResDTO response = userService.userSignIn(userSignInReqDTO);

    // Then
    assertNotNull(response);
  }

  @Test
  void signIn_shouldThrowException_whenUserInfoNotMatched() {

    // Given
    UserSignInReqDTO userSignInReqDTO = UserSignInReqDTO.builder()
        .userId("id3001")
        .password("wrong pwd")
        .build();

    // When & Then
    assertThrows(PasswordNotMatchException.class, () -> {
      userService.userSignIn(userSignInReqDTO);
    });
  }

  @Test
  void signIn_shouldThrowException_whenEntityNotExist() {

    // Given
    UserSignInReqDTO userSignInReqDTO = UserSignInReqDTO.builder()
        .userId("wrong id")
        .password("wrong pwd")
        .build();

    // When & Then
    assertThrows(UserNotExistException.class, () -> {
      userService.userSignIn(userSignInReqDTO);
    });
  }
}
