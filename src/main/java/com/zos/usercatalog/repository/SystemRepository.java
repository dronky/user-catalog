package com.zos.usercatalog.repository;

import com.zos.usercatalog.domain.System;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the System entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SystemRepository extends JpaRepository<System, Long> {

}
