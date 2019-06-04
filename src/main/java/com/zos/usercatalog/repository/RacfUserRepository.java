package com.zos.usercatalog.repository;

import com.zos.usercatalog.domain.RacfUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the RacfUser entity.
 */
@Repository
public interface RacfUserRepository extends JpaRepository<RacfUser, Long> {

    @Query(value = "select distinct racfUser from RacfUser racfUser left join fetch racfUser.groups left join fetch racfUser.systems",
        countQuery = "select count(distinct racfUser) from RacfUser racfUser")
    Page<RacfUser> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct racfUser from RacfUser racfUser left join fetch racfUser.groups left join fetch racfUser.systems")
    List<RacfUser> findAllWithEagerRelationships();

    @Query("select racfUser from RacfUser racfUser left join fetch racfUser.groups left join fetch racfUser.systems where racfUser.id =:id")
    Optional<RacfUser> findOneWithEagerRelationships(@Param("id") Long id);

}
