package com.sh.ytb.util;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

/* FIXME: Spring Session이 지혼자 다해서 필요가 없는 것같음 */
@Deprecated
public class AESUtils {

  public String encrypt(String data, SecretKey key)
      // TODO: 익셉션 핸들러에 추가
      throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

    Cipher cipher = Cipher.getInstance("AES");
    cipher.init(Cipher.ENCRYPT_MODE, key);
    byte[] encryptedData = cipher.doFinal(data.getBytes());
    return Base64.getEncoder().encodeToString(encryptedData);
  }

  public String decrypt(String encryptedData, SecretKey key)
      throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

    Cipher cipher = Cipher.getInstance("AES");
    cipher.init(Cipher.DECRYPT_MODE, key);
    byte[] decodedData = Base64.getDecoder().decode(encryptedData);
    byte[] decryptedData = cipher.doFinal(decodedData);
    return new String(decryptedData);
  }
}
