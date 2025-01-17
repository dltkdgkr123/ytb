package com.sh.ytb.module;

import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.TestPropertySource;

@DataRedisTest
@TestPropertySource(locations = "file:src/main/resources/application.properties")
public class RedisTests {

  @Autowired
  private StringRedisTemplate redisTemplate;

  @Test
  void testConnection() {

    // 1. ping-pong test

    // Given & When
    String pingResponse = redisTemplate.getConnectionFactory().getConnection().ping();

    // Then
    Assertions.assertEquals("PONG", pingResponse);

    // 2. Basic Operation Test

    // Given
    ValueOperations<String, String> ops = redisTemplate.opsForValue();
    ops.set("testKey", "testValue", 60, TimeUnit.SECONDS);

    // When
    String getResponse = ops.get("testKey");

    // Then
    Assertions.assertEquals("testValue", getResponse);
  }
}
