package com.zos.usercatalog.service;

import com.zos.usercatalog.service.dto.ArmIpDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.zos.usercatalog.domain.ArmIp}.
 */
public interface ArmIpService {

    /**
     * Save a armIp.
     *
     * @param armIpDTO the entity to save.
     * @return the persisted entity.
     */
    ArmIpDTO save(ArmIpDTO armIpDTO);

    /**
     * Get all the armIps.
     *
     * @return the list of entities.
     */
    List<ArmIpDTO> findAll();


    /**
     * Get the "id" armIp.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ArmIpDTO> findOne(Long id);

    /**
     * Delete the "id" armIp.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the armIp corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<ArmIpDTO> search(String query);
}
