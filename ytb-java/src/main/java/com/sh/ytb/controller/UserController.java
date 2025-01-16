package com.sh.ytb.controller;


import com.google.api.services.youtube.model.SearchResult;
import com.sh.ytb.dto.UserLoginDTO;
import com.sh.ytb.dto.UserRegistDTO;
import com.sh.ytb.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

  UserService userService;

  @PostMapping("/regist")
  ResponseEntity<List<SearchResult>> registUser(@RequestBody UserRegistDTO userRegistDTO) {

    userService.userRegist(userRegistDTO);

    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @PostMapping("/login")
  ResponseEntity<List<SearchResult>> loginUser(@RequestBody UserLoginDTO userLoginDTO) {

    userService.userLogin(userLoginDTO);

    return ResponseEntity.status(HttpStatus.OK).build();
  }
}
