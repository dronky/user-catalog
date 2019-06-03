package com.zos.usercatalog.repository;

import com.zos.usercatalog.domain.Arm;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Arm entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArmRepository extends JpaRepository<Arm, Long> {

}
