package com.sh.ytb;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class YoutubeService {

  private final CredentialProperties properties;

  String sayHello() {
    System.out.println("hello server");

    return "hello client";
  }
}
