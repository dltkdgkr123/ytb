package com.sh.ytb.adapter.out.jpa.repo;

import com.sh.ytb.adapter.out.jpa.entity.SubScribeJPAEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscribeRepository extends JpaRepository<SubScribeJPAEntity, Long> {

}
