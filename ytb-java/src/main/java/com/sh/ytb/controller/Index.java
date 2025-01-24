package com.sh.ytb.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
@RequiredArgsConstructor
public class Index {

  @PostMapping
  ResponseEntity<String> sayHello() {

    return ResponseEntity.status(HttpStatus.OK).body("Hello");
  }
}
