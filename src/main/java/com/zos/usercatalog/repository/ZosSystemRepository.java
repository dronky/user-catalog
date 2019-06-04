package com.zos.usercatalog.repository;

import com.zos.usercatalog.domain.ZosSystem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ZosSystem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ZosSystemRepository extends JpaRepository<ZosSystem, Long> {

}
