package com.sh.ytb.repository;

import com.sh.ytb.entity.SubScribeJPAEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscribeRepository extends JpaRepository<SubScribeJPAEntity, Long> {

}
