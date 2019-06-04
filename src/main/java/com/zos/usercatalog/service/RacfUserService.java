package com.zos.usercatalog.service;

import com.zos.usercatalog.service.dto.RacfUserDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.zos.usercatalog.domain.RacfUser}.
 */
public interface RacfUserService {

    /**
     * Save a racfUser.
     *
     * @param racfUserDTO the entity to save.
     * @return the persisted entity.
     */
    RacfUserDTO save(RacfUserDTO racfUserDTO);

    /**
     * Get all the racfUsers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RacfUserDTO> findAll(Pageable pageable);

    /**
     * Get all the racfUsers with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<RacfUserDTO> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" racfUser.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RacfUserDTO> findOne(Long id);

    /**
     * Delete the "id" racfUser.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the racfUser corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RacfUserDTO> search(String query, Pageable pageable);
}
