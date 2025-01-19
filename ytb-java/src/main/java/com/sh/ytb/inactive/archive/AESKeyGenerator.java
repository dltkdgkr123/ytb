package com.sh.ytb.inactive.archive;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import org.springframework.context.annotation.Profile;

@Profile("archive")
public class AESKeyGenerator {

  public static String generateAESKey() throws NoSuchAlgorithmException {
    KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
    keyGenerator.init(256, new SecureRandom()); // AES-256 사용
    SecretKey secretKey = keyGenerator.generateKey();

    return Base64.getEncoder().encodeToString(secretKey.getEncoded()); // Base64로 인코딩하여 반환
  }

  public static void main(String[] args) {
    try {
      String encodedKey = generateAESKey();
      System.out.println("Generated AES-256 Key (Base64): " + encodedKey);
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
  }
}
