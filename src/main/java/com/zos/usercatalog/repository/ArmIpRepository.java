package com.zos.usercatalog.repository;

import com.zos.usercatalog.domain.ArmIp;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the ArmIp entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArmIpRepository extends JpaRepository<ArmIp, Long> {

    List<ArmIp> findByArmId(Long armId);
}
