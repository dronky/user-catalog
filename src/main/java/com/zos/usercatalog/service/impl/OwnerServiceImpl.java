package com.zos.usercatalog.service.impl;

import com.zos.usercatalog.service.OwnerService;
import com.zos.usercatalog.domain.Owner;
import com.zos.usercatalog.repository.OwnerRepository;
import com.zos.usercatalog.repository.search.OwnerSearchRepository;
import com.zos.usercatalog.service.dto.OwnerDTO;
import com.zos.usercatalog.service.mapper.OwnerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Owner}.
 */
@Service
@Transactional
public class OwnerServiceImpl implements OwnerService {

    private final Logger log = LoggerFactory.getLogger(OwnerServiceImpl.class);

    private final OwnerRepository ownerRepository;

    private final OwnerMapper ownerMapper;

    private final OwnerSearchRepository ownerSearchRepository;

    public OwnerServiceImpl(OwnerRepository ownerRepository, OwnerMapper ownerMapper, OwnerSearchRepository ownerSearchRepository) {
        this.ownerRepository = ownerRepository;
        this.ownerMapper = ownerMapper;
        this.ownerSearchRepository = ownerSearchRepository;
    }

    /**
     * Save a owner.
     *
     * @param ownerDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public OwnerDTO save(OwnerDTO ownerDTO) {
        log.debug("Request to save Owner : {}", ownerDTO);
        Owner owner = ownerMapper.toEntity(ownerDTO);
        owner = ownerRepository.save(owner);
        OwnerDTO result = ownerMapper.toDto(owner);
        ownerSearchRepository.save(owner);
        return result;
    }

    /**
     * Get all the owners.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<OwnerDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Owners");
        return ownerRepository.findAll(pageable)
            .map(ownerMapper::toDto);
    }



    /**
    *  Get all the owners where Name is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<OwnerDTO> findAllWhereNameIsNull() {
        log.debug("Request to get all owners where Name is null");
        return StreamSupport
            .stream(ownerRepository.findAll().spliterator(), false)
            .filter(owner -> owner.getName() == null)
            .map(ownerMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one owner by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<OwnerDTO> findOne(Long id) {
        log.debug("Request to get Owner : {}", id);
        return ownerRepository.findById(id)
            .map(ownerMapper::toDto);
    }

    /**
     * Delete the owner by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Owner : {}", id);
        ownerRepository.deleteById(id);
        ownerSearchRepository.deleteById(id);
    }

    /**
     * Search for the owner corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<OwnerDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Owners for query {}", query);
        return ownerSearchRepository.search(queryStringQuery(query), pageable)
            .map(ownerMapper::toDto);
    }
}
