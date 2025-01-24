package com.sh.ytb.app.service;

import com.sh.ytb.app.dto.req.UserSignInReqDTO;
import com.sh.ytb.app.dto.req.UserSignUpReqDTO;
import com.sh.ytb.app.dto.res.UserSignInResDTO;
import com.sh.ytb.adapter.out.jpa.entity.UserJPAEntity;
import com.sh.ytb.common.exception.PasswordNotMatchException;
import com.sh.ytb.common.exception.UserNotExistException;
import com.sh.ytb.app.mapper.UserMapper;
import com.sh.ytb.adapter.out.jpa.repo.GoogleTokenRepository;
import com.sh.ytb.adapter.out.jpa.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final GoogleTokenRepository googleTokenRepository;
  private final UserMapper userMapper;
  private final PasswordEncoder passwordEncoder;

  public void userSignUp(UserSignUpReqDTO userSignUpReqDTO) {

    userRepository.save(userMapper.mapDTOToJPAEntity(userSignUpReqDTO));
  }

  @Transactional
  public UserSignInResDTO userSignIn(UserSignInReqDTO userSignInReqDTO) {

    UserJPAEntity userJPAEntity =
        userRepository.findByUserId(userSignInReqDTO.getUserId())
            .orElseThrow(() -> new UserNotExistException(userSignInReqDTO.getUserId()));

    boolean pwdMatched = passwordEncoder.matches(userSignInReqDTO.getPassword(),
        userJPAEntity.getPassword());

    if (!pwdMatched) {
      throw new PasswordNotMatchException(userJPAEntity.getUserId());
    }

    // TODO: 세션 추가

    return
        UserSignInResDTO.builder()
            .hasToken(googleTokenRepository.existsById(userJPAEntity.getId()))
            .build();
  }
}
