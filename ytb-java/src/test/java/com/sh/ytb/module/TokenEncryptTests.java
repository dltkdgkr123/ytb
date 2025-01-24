package com.sh.ytb.module;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.sh.ytb.common.config.spec.TokenCipher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@TestPropertySource(locations = "file:src/main/resources/application.properties")
public class TokenEncryptTests {

  @Autowired
  private TokenCipher tokenCipher;

  @Test
  void EncryptTest() {

    // Given
    String origin = "test data";

    // When
    String encrypted = tokenCipher.encrypt(origin);
    String decrypted = tokenCipher.decrypt(encrypted);

    // Then
    assertEquals("ogT/+rHKvSiw7ql0cZsaNQ==", encrypted);
    assertEquals(origin, decrypted);
  }

}
