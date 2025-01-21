package com.sh.ytb.service;

import com.sh.ytb.dto.req.UserSignInReqDTO;
import com.sh.ytb.dto.req.UserSignUpReqDTO;
import com.sh.ytb.entity.UserJPAEntity;
import com.sh.ytb.exception.PasswordNotMatchException;
import com.sh.ytb.exception.UserNotExistException;
import com.sh.ytb.mapper.UserMapper;
import com.sh.ytb.repository.UserRepository;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final OAuthService oAuthService;
  private final UserMapper userMapper;
  private final PasswordEncoder passwordEncoder;

  public void userSignUp(UserSignUpReqDTO userSignUpReqDTO) {

    userRepository.save(userMapper.mapDTOToJPAEntity(userSignUpReqDTO));
  }

  @Transactional
  public boolean userSignIn(UserSignInReqDTO userSignInReqDTO) throws IOException {

    UserJPAEntity userJPAEntity =
        userRepository.findByUserId(userSignInReqDTO.getUserId())
            .orElseThrow(() -> new UserNotExistException(userSignInReqDTO.getUserId()));

    boolean pwdMatched = passwordEncoder.matches(userSignInReqDTO.getPassword(),
        userJPAEntity.getPassword());

    if (!pwdMatched) {
      throw new PasswordNotMatchException(userSignInReqDTO.getUserId());
    }

    // 플로우 생각

    // 조되는 토큰이 없으면? 만들러가세용

    // 있으면? 로드해용

    // TODO: 세션 추가

    return true;
  }
}
