package com.sh.ytb.specs.impl;

import com.sh.ytb.specs.TokenCipher;
import com.sh.ytb.exception.AESCipherInvalidException;
import com.sh.ytb.properties.secret.AESProperties;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class AESCipher implements TokenCipher {

  private final AESProperties aesProperties;

  public AESCipher(AESProperties aesProperties) {
    this.aesProperties = aesProperties;
  }


  public String encrypt(String data) {

    try {
      Cipher cipher = Cipher.getInstance("AES");
      cipher.init(Cipher.ENCRYPT_MODE, aesProperties.getSecretKeyForAES());
      byte[] encryptedData = cipher.doFinal(data.getBytes());

      return Base64.getEncoder().encodeToString(encryptedData);
    } catch (
        NoSuchPaddingException
        | NoSuchAlgorithmException
        | InvalidKeyException
        | IllegalBlockSizeException
        | BadPaddingException e) {

      throw new AESCipherInvalidException("AES encryption failed", e);
    }
  }

  public String decrypt(String encryptedData) {

    try {
      Cipher cipher = Cipher.getInstance("AES");
      cipher.init(Cipher.DECRYPT_MODE, aesProperties.getSecretKeyForAES());
      byte[] decodedData = Base64.getDecoder().decode(encryptedData);
      byte[] decryptedData = cipher.doFinal(decodedData);

      return new String(decryptedData);
    } catch (
        NoSuchPaddingException
        | NoSuchAlgorithmException
        | InvalidKeyException
        | IllegalBlockSizeException
        | BadPaddingException e) {

      throw new AESCipherInvalidException("AES decryption failed", e);
    }
  }
}
