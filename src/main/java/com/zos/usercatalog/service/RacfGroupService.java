package com.zos.usercatalog.service;

import com.zos.usercatalog.service.dto.RacfGroupDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.zos.usercatalog.domain.RacfGroup}.
 */
public interface RacfGroupService {

    /**
     * Save a racfGroup.
     *
     * @param racfGroupDTO the entity to save.
     * @return the persisted entity.
     */
    RacfGroupDTO save(RacfGroupDTO racfGroupDTO);

    /**
     * Get all the racfGroups.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RacfGroupDTO> findAll(Pageable pageable);


    /**
     * Get the "id" racfGroup.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RacfGroupDTO> findOne(Long id);

    /**
     * Delete the "id" racfGroup.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the racfGroup corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RacfGroupDTO> search(String query, Pageable pageable);
}
