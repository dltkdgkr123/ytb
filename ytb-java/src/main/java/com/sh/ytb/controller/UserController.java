package com.sh.ytb.controller;


import com.sh.ytb.dto.req.UserSignInReqDTO;
import com.sh.ytb.dto.req.UserSignUpReqDTO;
import com.sh.ytb.dto.res.UserSignInResDTO;
import com.sh.ytb.service.UserService;
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


  @PostMapping("/sign-up")
  ResponseEntity<Void> signUpUser(@RequestBody UserSignUpReqDTO userSignUpReqDTO) {

    userService.userSignUp(userSignUpReqDTO);

    return ResponseEntity.status(HttpStatus.OK).build();
  }


  @PostMapping("/sign-in")
  ResponseEntity<UserSignInResDTO> signInUser(@RequestBody UserSignInReqDTO userSignInReqDTO) {

    UserSignInResDTO results = userService.userSignIn(userSignInReqDTO);

    return ResponseEntity.status(HttpStatus.OK).body(results);
  }
}
