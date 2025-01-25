package com.sh.ytb.adapter.in.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("index-controller") // index bean이 스프링에 이미 있는 듯 함
@RequestMapping("/home")
@RequiredArgsConstructor
public class Index {

  @GetMapping
  ResponseEntity<String> sayHello() {

    return ResponseEntity.status(HttpStatus.OK).body("Hello");
  }
}
