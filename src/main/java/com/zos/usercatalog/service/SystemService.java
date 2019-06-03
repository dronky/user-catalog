package com.zos.usercatalog.service;

import com.zos.usercatalog.service.dto.SystemDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.zos.usercatalog.domain.System}.
 */
public interface SystemService {

    /**
     * Save a system.
     *
     * @param systemDTO the entity to save.
     * @return the persisted entity.
     */
    SystemDTO save(SystemDTO systemDTO);

    /**
     * Get all the systems.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SystemDTO> findAll(Pageable pageable);


    /**
     * Get the "id" system.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SystemDTO> findOne(Long id);

    /**
     * Delete the "id" system.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the system corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SystemDTO> search(String query, Pageable pageable);
}
