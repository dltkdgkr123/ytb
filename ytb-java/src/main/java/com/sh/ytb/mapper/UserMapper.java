package com.sh.ytb.mapper;

import com.sh.ytb.dto.UserSignUpDTO;
import com.sh.ytb.entity.UserJPAEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

  private PasswordEncoder passwordEncoder;

  public UserJPAEntity mapDTOToJPAEntity(UserSignUpDTO userSignUpDTO) {

    return
        UserJPAEntity.builder()
            .id(null) // TODO: 체크
            .userId(userSignUpDTO.getUserId())
            .password(passwordEncoder.encode(userSignUpDTO.getPassword()))
            .build();
  }
}
