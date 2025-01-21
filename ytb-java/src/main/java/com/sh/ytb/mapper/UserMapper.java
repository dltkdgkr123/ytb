package com.sh.ytb.mapper;

import com.sh.ytb.dto.req.UserSignUpReqDTO;
import com.sh.ytb.entity.UserJPAEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

  private PasswordEncoder passwordEncoder;

  public UserJPAEntity mapDTOToJPAEntity(UserSignUpReqDTO userSignUpReqDTO) {

    return
        UserJPAEntity.builder()
            .id(null) // TODO: 체크
            .userId(userSignUpReqDTO.getUserId())
            .password(passwordEncoder.encode(userSignUpReqDTO.getPassword()))
            .build();
  }
}
