package com.zos.usercatalog.service.impl;

import com.zos.usercatalog.service.RacfGroupService;
import com.zos.usercatalog.domain.RacfGroup;
import com.zos.usercatalog.repository.RacfGroupRepository;
import com.zos.usercatalog.repository.search.RacfGroupSearchRepository;
import com.zos.usercatalog.service.dto.RacfGroupDTO;
import com.zos.usercatalog.service.mapper.RacfGroupMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link RacfGroup}.
 */
@Service
@Transactional
public class RacfGroupServiceImpl implements RacfGroupService {

    private final Logger log = LoggerFactory.getLogger(RacfGroupServiceImpl.class);

    private final RacfGroupRepository racfGroupRepository;

    private final RacfGroupMapper racfGroupMapper;

    private final RacfGroupSearchRepository racfGroupSearchRepository;

    public RacfGroupServiceImpl(RacfGroupRepository racfGroupRepository, RacfGroupMapper racfGroupMapper, RacfGroupSearchRepository racfGroupSearchRepository) {
        this.racfGroupRepository = racfGroupRepository;
        this.racfGroupMapper = racfGroupMapper;
        this.racfGroupSearchRepository = racfGroupSearchRepository;
    }

    /**
     * Save a racfGroup.
     *
     * @param racfGroupDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public RacfGroupDTO save(RacfGroupDTO racfGroupDTO) {
        log.debug("Request to save RacfGroup : {}", racfGroupDTO);
        RacfGroup racfGroup = racfGroupMapper.toEntity(racfGroupDTO);
        racfGroup = racfGroupRepository.save(racfGroup);
        RacfGroupDTO result = racfGroupMapper.toDto(racfGroup);
        racfGroupSearchRepository.save(racfGroup);
        return result;
    }

    /**
     * Get all the racfGroups.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RacfGroupDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RacfGroups");
        return racfGroupRepository.findAll(pageable)
            .map(racfGroupMapper::toDto);
    }


    /**
     * Get one racfGroup by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<RacfGroupDTO> findOne(Long id) {
        log.debug("Request to get RacfGroup : {}", id);
        return racfGroupRepository.findById(id)
            .map(racfGroupMapper::toDto);
    }

    /**
     * Delete the racfGroup by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RacfGroup : {}", id);
        racfGroupRepository.deleteById(id);
        racfGroupSearchRepository.deleteById(id);
    }

    /**
     * Search for the racfGroup corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RacfGroupDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of RacfGroups for query {}", query);
        return racfGroupSearchRepository.search(queryStringQuery(query), pageable)
            .map(racfGroupMapper::toDto);
    }
}
