package com.sh.ytb.controller;


import com.google.api.services.youtube.model.SearchResult;
import com.sh.ytb.dto.UserSignInDTO;
import com.sh.ytb.dto.UserSignUpDTO;
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

  @PostMapping("/sign-up")
  ResponseEntity<List<SearchResult>> signUpUser(@RequestBody UserSignUpDTO userSignUpDTO) {

    userService.userSignUp(userSignUpDTO);

    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @PostMapping("/sign-in")
  ResponseEntity<List<SearchResult>> signInUser(@RequestBody UserSignInDTO userSignInDTO) {

    userService.userSignIn(userSignInDTO);

    return ResponseEntity.status(HttpStatus.OK).build();
  }
}
