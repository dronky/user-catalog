package com.zos.usercatalog.service.impl;

import com.zos.usercatalog.service.ZosSystemService;
import com.zos.usercatalog.domain.ZosSystem;
import com.zos.usercatalog.repository.ZosSystemRepository;
import com.zos.usercatalog.repository.search.ZosSystemSearchRepository;
import com.zos.usercatalog.service.dto.ZosSystemDTO;
import com.zos.usercatalog.service.mapper.ZosSystemMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link ZosSystem}.
 */
@Service
@Transactional
public class ZosSystemServiceImpl implements ZosSystemService {

    private final Logger log = LoggerFactory.getLogger(ZosSystemServiceImpl.class);

    private final ZosSystemRepository zosSystemRepository;

    private final ZosSystemMapper zosSystemMapper;

    private final ZosSystemSearchRepository zosSystemSearchRepository;

    public ZosSystemServiceImpl(ZosSystemRepository zosSystemRepository, ZosSystemMapper zosSystemMapper, ZosSystemSearchRepository zosSystemSearchRepository) {
        this.zosSystemRepository = zosSystemRepository;
        this.zosSystemMapper = zosSystemMapper;
        this.zosSystemSearchRepository = zosSystemSearchRepository;
    }

    /**
     * Save a zosSystem.
     *
     * @param zosSystemDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ZosSystemDTO save(ZosSystemDTO zosSystemDTO) {
        log.debug("Request to save ZosSystem : {}", zosSystemDTO);
        ZosSystem zosSystem = zosSystemMapper.toEntity(zosSystemDTO);
        zosSystem = zosSystemRepository.save(zosSystem);
        ZosSystemDTO result = zosSystemMapper.toDto(zosSystem);
        zosSystemSearchRepository.save(zosSystem);
        return result;
    }

    /**
     * Get all the zosSystems.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ZosSystemDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ZosSystems");
        return zosSystemRepository.findAll(pageable)
            .map(zosSystemMapper::toDto);
    }


    /**
     * Get one zosSystem by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ZosSystemDTO> findOne(Long id) {
        log.debug("Request to get ZosSystem : {}", id);
        return zosSystemRepository.findById(id)
            .map(zosSystemMapper::toDto);
    }

    /**
     * Delete the zosSystem by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ZosSystem : {}", id);
        zosSystemRepository.deleteById(id);
        zosSystemSearchRepository.deleteById(id);
    }

    /**
     * Search for the zosSystem corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ZosSystemDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ZosSystems for query {}", query);
        return zosSystemSearchRepository.search(queryStringQuery(query), pageable)
            .map(zosSystemMapper::toDto);
    }
}
