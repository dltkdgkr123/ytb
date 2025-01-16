package com.sh.ytb.service;

import com.sh.ytb.dto.UserLoginDTO;
import com.sh.ytb.dto.UserRegistDTO;
import com.sh.ytb.entity.UserJPAEntity;
import com.sh.ytb.exception.UserNotExistException;
import com.sh.ytb.mapper.UserMapper;
import com.sh.ytb.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;
  private final PasswordEncoder passwordEncoder;

  public void userRegist(UserRegistDTO userRegistDTO) {

    userRepository.save(userMapper.mapObjToDTO(userRegistDTO));
  }

  public void userLogin(UserLoginDTO userLoginDTO) {

    UserJPAEntity userJPAEntity =
        userRepository.findByName(userLoginDTO.getName())
            .orElseThrow(() -> new UserNotExistException(userLoginDTO.getName()));

    boolean pwdMatched = passwordEncoder.matches(userLoginDTO.getPassword(),
        userJPAEntity.getPassword());

    // TODO: 세션추가, 구글토큰 insert, return Entity

  }
}
