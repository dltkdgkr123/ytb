package com.sh.ytb.repository;

import com.sh.ytb.entity.UserJPAEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserJPAEntity, Long> {

  Optional<UserJPAEntity> findByName(String name);
}
