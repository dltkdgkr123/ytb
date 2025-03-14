package com.sh.ytb.common.config.impl;

import com.sh.ytb.common.config.spec.SessionSerializer;
import com.sh.ytb.common.exception.SessionSerializerInvalidException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Base64;

public class CustomSerializer implements SessionSerializer {

  @Override
  public String Base64StringToString(String s) {
    StringBuilder sb = new StringBuilder();
    byte[] decodedBytes = Base64.getDecoder().decode(s.getBytes());

    for (byte b : decodedBytes) {
      sb.append((char) b);
    }

    return sb.toString();
  }

  @Override
  public String ByteStreamToString(byte[] serialized) {
//    StringBuilder sb = new StringBuilder();
//
//    for(byte b : Base64.getDecoder().decode(serialized)) {
//      sb.append((char) b);
//    }
//    return sb.toString();

    try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(serialized))) {

      return (String) ois.readObject();
    } catch (IOException | ClassNotFoundException e) {
      throw new SessionSerializerInvalidException("deserialization failed", e);
    }
  }
}
