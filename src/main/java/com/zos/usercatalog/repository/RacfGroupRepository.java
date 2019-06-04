package com.zos.usercatalog.repository;

import com.zos.usercatalog.domain.RacfGroup;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the RacfGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RacfGroupRepository extends JpaRepository<RacfGroup, Long> {

}
