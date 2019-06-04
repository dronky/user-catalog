package com.zos.usercatalog.service.impl;

import com.zos.usercatalog.service.RacfUserService;
import com.zos.usercatalog.domain.RacfUser;
import com.zos.usercatalog.repository.RacfUserRepository;
import com.zos.usercatalog.repository.search.RacfUserSearchRepository;
import com.zos.usercatalog.service.dto.RacfUserDTO;
import com.zos.usercatalog.service.mapper.RacfUserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link RacfUser}.
 */
@Service
@Transactional
public class RacfUserServiceImpl implements RacfUserService {

    private final Logger log = LoggerFactory.getLogger(RacfUserServiceImpl.class);

    private final RacfUserRepository racfUserRepository;

    private final RacfUserMapper racfUserMapper;

    private final RacfUserSearchRepository racfUserSearchRepository;

    public RacfUserServiceImpl(RacfUserRepository racfUserRepository, RacfUserMapper racfUserMapper, RacfUserSearchRepository racfUserSearchRepository) {
        this.racfUserRepository = racfUserRepository;
        this.racfUserMapper = racfUserMapper;
        this.racfUserSearchRepository = racfUserSearchRepository;
    }

    /**
     * Save a racfUser.
     *
     * @param racfUserDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public RacfUserDTO save(RacfUserDTO racfUserDTO) {
        log.debug("Request to save RacfUser : {}", racfUserDTO);
        RacfUser racfUser = racfUserMapper.toEntity(racfUserDTO);
        racfUser = racfUserRepository.save(racfUser);
        RacfUserDTO result = racfUserMapper.toDto(racfUser);
        racfUserSearchRepository.save(racfUser);
        return result;
    }

    /**
     * Get all the racfUsers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RacfUserDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RacfUsers");
        return racfUserRepository.findAll(pageable)
            .map(racfUserMapper::toDto);
    }

    /**
     * Get all the racfUsers with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<RacfUserDTO> findAllWithEagerRelationships(Pageable pageable) {
        return racfUserRepository.findAllWithEagerRelationships(pageable).map(racfUserMapper::toDto);
    }
    

    /**
     * Get one racfUser by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<RacfUserDTO> findOne(Long id) {
        log.debug("Request to get RacfUser : {}", id);
        return racfUserRepository.findOneWithEagerRelationships(id)
            .map(racfUserMapper::toDto);
    }

    /**
     * Delete the racfUser by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RacfUser : {}", id);
        racfUserRepository.deleteById(id);
        racfUserSearchRepository.deleteById(id);
    }

    /**
     * Search for the racfUser corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RacfUserDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of RacfUsers for query {}", query);
        return racfUserSearchRepository.search(queryStringQuery(query), pageable)
            .map(racfUserMapper::toDto);
    }
}
