package com.sh.ytb.adapter.in.web;


import com.sh.ytb.app.dto.req.UserSignInReqDTO;
import com.sh.ytb.app.dto.req.UserSignUpReqDTO;
import com.sh.ytb.app.dto.res.UserSignInResDTO;
import com.sh.ytb.app.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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
  ResponseEntity<UserSignInResDTO> signInUser(final HttpServletRequest httpRequest,
      @RequestBody UserSignInReqDTO userSignInReqDTO) {

    UserSignInResDTO results = userService.userSignIn(userSignInReqDTO);

    /*
    * Spring Session에 의해 세션 자동 처리
    * 현재 on_save 될 때, 새로 생성한 sessionID를 Base64 인코딩하여 Redis ytb:sessison:sessions 경로에 저장
    * ref: https://brightmango.tistory.com/entry/%EC%8A%A4%ED%94%84%EB%A7%81-%EC%84%B8%EC%85%98-%EC%9D%B4%ED%95%B4-%EC%9D%B4%EC%A4%91-%EC%9D%B8%EC%BD%94%EB%94%A9%EB%90%9C-%EC%84%B8%EC%85%98-%ED%95%B4%EA%B2%B0
    */
    HttpSession httpSession = httpRequest.getSession(true);
    httpSession.setAttribute("userId", userSignInReqDTO.getUserId());
    
    return ResponseEntity.status(HttpStatus.OK).body(results);
  }
}
