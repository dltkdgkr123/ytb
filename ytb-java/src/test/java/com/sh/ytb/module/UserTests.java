package com.sh.ytb.module;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.sh.ytb.dto.UserSignInDTO;
import com.sh.ytb.exception.PasswordNotMatchException;
import com.sh.ytb.exception.UserNotExistException;
import com.sh.ytb.service.UserService;
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
    UserSignInDTO userSignInDTO = UserSignInDTO.builder()
        .userId("id3001")
        .password("password3001")
        .build();

    // When
    boolean success = userService.userSignIn(userSignInDTO);

    // Then
    assertTrue(success);
  }

  @Test
  void signIn_shouldThrowException_whenUserInfoNotMatched() {

    // Given
    UserSignInDTO userSignInDTO = UserSignInDTO.builder()
        .userId("id3001")
        .password("wrong pwd")
        .build();

    // When & Then
    assertThrows(PasswordNotMatchException.class, () -> {
      userService.userSignIn(userSignInDTO);
    });
  }

  @Test
  void signIn_shouldThrowException_whenEntityNotExist() {

    // Given
    UserSignInDTO userSignInDTO = UserSignInDTO.builder()
        .userId("wrong id")
        .password("wrong pwd")
        .build();

    // When & Then
    assertThrows(UserNotExistException.class, () -> {
      userService.userSignIn(userSignInDTO);
    });
  }
}
