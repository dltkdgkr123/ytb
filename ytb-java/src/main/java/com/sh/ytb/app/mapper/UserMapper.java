package com.sh.ytb.app.mapper;

import com.sh.ytb.app.dto.req.UserSignUpReqDTO;
import com.sh.ytb.adapter.out.jpa.entity.UserJPAEntity;
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
//            .id()
            .userId(userSignUpReqDTO.getUserId())
            .password(passwordEncoder.encode(userSignUpReqDTO.getPassword()))
            .build();
  }
}
