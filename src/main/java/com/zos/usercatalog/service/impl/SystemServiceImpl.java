package com.zos.usercatalog.service.impl;

import com.zos.usercatalog.service.SystemService;
import com.zos.usercatalog.domain.System;
import com.zos.usercatalog.repository.SystemRepository;
import com.zos.usercatalog.repository.search.SystemSearchRepository;
import com.zos.usercatalog.service.dto.SystemDTO;
import com.zos.usercatalog.service.mapper.SystemMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link System}.
 */
@Service
@Transactional
public class SystemServiceImpl implements SystemService {

    private final Logger log = LoggerFactory.getLogger(SystemServiceImpl.class);

    private final SystemRepository systemRepository;

    private final SystemMapper systemMapper;

    private final SystemSearchRepository systemSearchRepository;

    public SystemServiceImpl(SystemRepository systemRepository, SystemMapper systemMapper, SystemSearchRepository systemSearchRepository) {
        this.systemRepository = systemRepository;
        this.systemMapper = systemMapper;
        this.systemSearchRepository = systemSearchRepository;
    }

    /**
     * Save a system.
     *
     * @param systemDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public SystemDTO save(SystemDTO systemDTO) {
        log.debug("Request to save System : {}", systemDTO);
        System system = systemMapper.toEntity(systemDTO);
        system = systemRepository.save(system);
        SystemDTO result = systemMapper.toDto(system);
        systemSearchRepository.save(system);
        return result;
    }

    /**
     * Get all the systems.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SystemDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Systems");
        return systemRepository.findAll(pageable)
            .map(systemMapper::toDto);
    }


    /**
     * Get one system by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SystemDTO> findOne(Long id) {
        log.debug("Request to get System : {}", id);
        return systemRepository.findById(id)
            .map(systemMapper::toDto);
    }

    /**
     * Delete the system by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete System : {}", id);
        systemRepository.deleteById(id);
        systemSearchRepository.deleteById(id);
    }

    /**
     * Search for the system corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SystemDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Systems for query {}", query);
        return systemSearchRepository.search(queryStringQuery(query), pageable)
            .map(systemMapper::toDto);
    }
}
