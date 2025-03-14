package com.sh.ytb.common.config.spec;

public interface SessionSerializer {

  String Base64StringToString(String s);

  String ByteStreamToString(byte[] serialized);

  // 요구사항 기술
}
