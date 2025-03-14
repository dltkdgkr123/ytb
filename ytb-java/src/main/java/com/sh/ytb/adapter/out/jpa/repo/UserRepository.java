package com.sh.ytb.adapter.out.jpa.repo;

import com.sh.ytb.adapter.out.jpa.entity.UserJPAEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserJPAEntity, Long> {

  Optional<UserJPAEntity> findByUserId(String userId);
}