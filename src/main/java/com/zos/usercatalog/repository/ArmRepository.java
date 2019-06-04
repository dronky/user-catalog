package com.zos.usercatalog.repository;

import com.zos.usercatalog.domain.Arm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Arm entity.
 */
@Repository
public interface ArmRepository extends JpaRepository<Arm, Long> {

    @Query(value = "select distinct arm from Arm arm left join fetch arm.armIps",
        countQuery = "select count(distinct arm) from Arm arm")
    Page<Arm> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct arm from Arm arm left join fetch arm.armIps")
    List<Arm> findAllWithEagerRelationships();

    @Query("select arm from Arm arm left join fetch arm.armIps where arm.id =:id")
    Optional<Arm> findOneWithEagerRelationships(@Param("id") Long id);

}
