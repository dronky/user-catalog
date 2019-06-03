package com.zos.usercatalog.repository;

import com.zos.usercatalog.domain.ArmIp;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ArmIp entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArmIpRepository extends JpaRepository<ArmIp, Long> {

}
