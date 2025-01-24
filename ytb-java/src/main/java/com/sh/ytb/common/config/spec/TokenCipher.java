package com.sh.ytb.common.config.spec;

public interface TokenCipher {

  String encrypt(String data);

  String decrypt(String encryptedData);
  
  // 요구사항 기술
}
