package com.sh.ytb;

import org.springframework.stereotype.Service;

@Service
public class YoutubeService {

  String sayHello() {
    System.out.println("hello server");

    return "hello client";
  }
}
