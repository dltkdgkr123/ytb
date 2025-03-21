package com.sh.ytb.adapter.out.jpa.repo;

import com.sh.ytb.adapter.out.jpa.entity.GoogleTokenJPAEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoogleTokenRepository extends JpaRepository<GoogleTokenJPAEntity, Long> {

  Optional<GoogleTokenJPAEntity> findByUserId(Long userId);
}
