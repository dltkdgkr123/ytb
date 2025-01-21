package com.sh.ytb.specs;

public interface TokenCipher {

  String encrypt(String data);

  String decrypt(String encryptedData);
}
