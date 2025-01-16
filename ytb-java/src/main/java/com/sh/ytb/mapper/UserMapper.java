package com.sh.ytb.mapper;

import com.sh.ytb.dto.UserRegistDTO;
import com.sh.ytb.entity.UserJPAEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

  private PasswordEncoder passwordEncoder;

  public UserJPAEntity mapObjToDTO(UserRegistDTO userRegistDTO) {

    return
        UserJPAEntity.builder()
            .id(userRegistDTO.getName())
            .password(passwordEncoder.encode(userRegistDTO.getPassword()))
            .build();
  }
}
