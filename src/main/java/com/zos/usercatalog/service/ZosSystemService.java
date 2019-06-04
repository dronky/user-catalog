package com.zos.usercatalog.service;

import com.zos.usercatalog.service.dto.ZosSystemDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.zos.usercatalog.domain.ZosSystem}.
 */
public interface ZosSystemService {

    /**
     * Save a zosSystem.
     *
     * @param zosSystemDTO the entity to save.
     * @return the persisted entity.
     */
    ZosSystemDTO save(ZosSystemDTO zosSystemDTO);

    /**
     * Get all the zosSystems.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ZosSystemDTO> findAll(Pageable pageable);


    /**
     * Get the "id" zosSystem.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ZosSystemDTO> findOne(Long id);

    /**
     * Delete the "id" zosSystem.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the zosSystem corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ZosSystemDTO> search(String query, Pageable pageable);
}
