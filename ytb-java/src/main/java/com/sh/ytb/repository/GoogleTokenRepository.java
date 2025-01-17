package com.sh.ytb.repository;

import com.sh.ytb.entity.GoogleTokenJPAEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoogleTokenRepository extends JpaRepository<GoogleTokenJPAEntity, Long> {

}
