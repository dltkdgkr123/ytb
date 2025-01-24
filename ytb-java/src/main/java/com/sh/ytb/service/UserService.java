package com.sh.ytb.service;

import com.sh.ytb.dto.req.UserSignInReqDTO;
import com.sh.ytb.dto.req.UserSignUpReqDTO;
import com.sh.ytb.dto.res.UserSignInResDTO;
import com.sh.ytb.entity.UserJPAEntity;
import com.sh.ytb.exception.PasswordNotMatchException;
import com.sh.ytb.exception.UserNotExistException;
import com.sh.ytb.mapper.UserMapper;
import com.sh.ytb.repository.GoogleTokenRepository;
import com.sh.ytb.repository.UserRepository;
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
