package com.sh.ytb.service;

import com.sh.ytb.dto.UserSignInDTO;
import com.sh.ytb.dto.UserSignUpDTO;
import com.sh.ytb.entity.UserJPAEntity;
import com.sh.ytb.exception.PasswordNotMatchException;
import com.sh.ytb.exception.UserNotExistException;
import com.sh.ytb.mapper.UserMapper;
import com.sh.ytb.repository.UserRepository;
import com.sh.ytb.util.SessionUtil;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;
  private final PasswordEncoder passwordEncoder;
  private final SessionUtil sessionUtil;


  public void userSignUp(UserSignUpDTO userSignUpDTO) {

    userRepository.save(userMapper.mapDTOToJPAEntity(userSignUpDTO));
  }

  public boolean userSignIn(UserSignInDTO userSignInDTO) {

    UserJPAEntity userJPAEntity =
        userRepository.findByUserId(userSignInDTO.getUserId())
            .orElseThrow(() -> new UserNotExistException(userSignInDTO.getUserId()));

    boolean pwdMatched = passwordEncoder.matches(userSignInDTO.getPassword(),
        userJPAEntity.getPassword());

    if (!pwdMatched) {
      throw new PasswordNotMatchException(userSignInDTO.getUserId());
    }

    HttpSession session = sessionUtil.getSession();

    // TODO: 쿠키 운용
    session.setAttribute("id", userJPAEntity.getId());

    return true;
  }
}
