package com.zos.usercatalog.service;

import com.zos.usercatalog.service.dto.ArmDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.zos.usercatalog.domain.Arm}.
 */
public interface ArmService {

    /**
     * Save a arm.
     *
     * @param armDTO the entity to save.
     * @return the persisted entity.
     */
    ArmDTO save(ArmDTO armDTO);

    /**
     * Get all the arms.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ArmDTO> findAll(Pageable pageable);

    /**
     * Get all the arms with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<ArmDTO> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" arm.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ArmDTO> findOne(Long id);

    /**
     * Delete the "id" arm.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the arm corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ArmDTO> search(String query, Pageable pageable);
}
