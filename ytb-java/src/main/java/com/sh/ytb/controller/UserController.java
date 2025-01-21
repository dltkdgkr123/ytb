package com.sh.ytb.controller;


import com.google.api.services.youtube.model.SearchResult;
import com.sh.ytb.dto.UserSignInReqDTO;
import com.sh.ytb.dto.UserSignUpReqDTO;
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

  private final UserService userService;

  /* TODO: 반환 값 정의 */
  @PostMapping("/sign-up")
  ResponseEntity<?> signUpUser(@RequestBody UserSignUpReqDTO userSignUpReqDTO) {

    userService.userSignUp(userSignUpReqDTO);

    return ResponseEntity.status(HttpStatus.OK).build();
  }

  /* TODO: 반환 값 정의 */
  @PostMapping("/sign-in")
  ResponseEntity<?> signInUser(@RequestBody UserSignInReqDTO userSignInReqDTO) {

    userService.userSignIn(userSignInReqDTO);

    return ResponseEntity.status(HttpStatus.OK).build();
  }
}
