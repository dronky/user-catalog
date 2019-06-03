package com.zos.usercatalog.service;

import com.zos.usercatalog.service.dto.OwnerDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.zos.usercatalog.domain.Owner}.
 */
public interface OwnerService {

    /**
     * Save a owner.
     *
     * @param ownerDTO the entity to save.
     * @return the persisted entity.
     */
    OwnerDTO save(OwnerDTO ownerDTO);

    /**
     * Get all the owners.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<OwnerDTO> findAll(Pageable pageable);
    /**
     * Get all the OwnerDTO where RacfUser is {@code null}.
     *
     * @return the list of entities.
     */
    List<OwnerDTO> findAllWhereRacfUserIsNull();


    /**
     * Get the "id" owner.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OwnerDTO> findOne(Long id);

    /**
     * Delete the "id" owner.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the owner corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<OwnerDTO> search(String query, Pageable pageable);
}
